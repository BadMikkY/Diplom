package com.example.diplom.screen.workerSettings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.api.SpecialistApi
import com.example.diplom.model.Review
import com.example.diplom.model.Specialist
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.navigation.Destination
import com.example.diplom.repository.SharedPreferencesRepository
import com.example.diplom.repository.SpecialistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WorkerSettingsScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val specialistRepository: SpecialistRepository
) : ViewModel() {
    fun getSpecMail() = sharedPreferencesRepository.getSpecialistMail()
    fun getSpecName() = sharedPreferencesRepository.getSpecName()
    fun getSpecExp() = sharedPreferencesRepository.getSpecExp()
    fun getSpecRates() = sharedPreferencesRepository.getSpecRates()
    fun getSpecSkills() = sharedPreferencesRepository.getSpecSkills()
    fun getSpecIDD() = sharedPreferencesRepository.getSpecId()

    private val _reviewsList = MutableStateFlow<List<Review>>(emptyList())
    val reviewsListState: Flow<List<Review>> get() = _reviewsList

    init {
        loadReviews()
    }

    private fun loadReviews() {
        val specId = sharedPreferencesRepository.getSpecId()
        viewModelScope.launch {
            try {
                _reviewsList.value =
                    specialistRepository.getReviews(SpecialistApi.ReviewRequest(specId))
            } catch (e: Exception) {
                when (e) {
                    is IOException -> {
                        Log.e(
                            "WorkerSettingsScreenViewModel",
                            "Network error: ${e.localizedMessage}"
                        )
                    }

                    is HttpException -> {
                        Log.e("WorkerSettingsScreenViewModel", "HTTP error: ${e.code()}")
                    }

                    else -> {
                        Log.e(
                            "WorkerSettingsScreenViewModel",
                            "Unknown error: ${e.localizedMessage}"
                        )
                    }
                }
            }
        }
    }

    fun handleEvent(workerSettingsEvent: WorkerSettingsEvent) {
        when (workerSettingsEvent) {
            is WorkerSettingsEvent.LogOutButtonClicked -> {
                viewModelScope.launch {
                    viewModelScope.launch(Dispatchers.IO) {
                        appNavigator.navigateTo(Destination.AuthorizationScreen())
                    }
                }
            }

            is WorkerSettingsEvent.ChangeButtonClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val specId = getSpecIDD()
                    val updatedSpec = Specialist(
                        SpecialistID = specId,
                        Email = "",
                        SpecName = workerSettingsEvent.specname,
                        Password = workerSettingsEvent.password
                    )

                    try{
                        specialistRepository.updateSpec(updatedSpec)
                    }catch (e:Exception){

                    }
                }
            }
        }
    }
}
