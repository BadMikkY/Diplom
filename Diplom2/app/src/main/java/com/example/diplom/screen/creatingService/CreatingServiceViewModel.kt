package com.example.diplom.screen.creatingService

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.model.Service
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.navigation.Destination
import com.example.diplom.repository.ServicesRepository
import com.example.diplom.repository.SharedPreferencesRepository
import com.example.diplom.repository.SpecialistRepository
import com.example.diplom.repository.UserRepository
import com.example.diplom.screen.authorization.AuthorizationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatingServiceViewModel @Inject constructor(
    private val repository: ServicesRepository,
    private val sharedPreferencesRepisitory: SharedPreferencesRepository,
    private val appNavigator: AppNavigator
) : ViewModel() {
    var onEmptyTextFields: (() -> Unit)? = null

    var ServiceName by mutableStateOf("")
        private set


    var error by mutableStateOf("")

    var Description by mutableStateOf("")
        private set

    fun updateServiceName(input: String) {
        ServiceName = input
    }

    var UserID by mutableStateOf(sharedPreferencesRepisitory.getUserId())
        private set


    fun updateDescription(input: String) {
        Description = input
    }


    fun handleEvent(creatingServiceEvent: CreatingServiceEvent) {
        when (creatingServiceEvent) {
            is CreatingServiceEvent.ConfirmButtonClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val service = Service(ServiceName, Description, UserID)
                        val response = repository.createService(service)
                        if (response.isSuccessful) {
                            response.body()?.let { serviceResponse ->
                                serviceResponse.ServiceName?.let { sharedPreferencesRepisitory.setServiceName(it) }
                                serviceResponse.Description?.let { sharedPreferencesRepisitory.setDescription(it) }
                                sharedPreferencesRepisitory.setServiceId(serviceResponse.ServiceId)
                            }
                        } else {
                            error = "Ошибка создания сервиса. Пожалуйста, проверьте введенные данные."
                        }
                    } catch (e: Exception) {
                        error = "Произошла ошибка: ${e.message}"
                    }
                }
            }
        }
    }
}