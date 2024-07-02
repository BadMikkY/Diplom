package com.example.diplom.screen.main

import android.health.connect.datatypes.BloodGlucoseRecord.SpecimenSource
import retrofit2.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.api.SpecialistApi
import com.example.diplom.model.Booking
import com.example.diplom.model.Review
import com.example.diplom.model.Service
import com.example.diplom.model.ServiceResponse
import com.example.diplom.model.Specialist
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.repository.ServicesRepository
import com.example.diplom.repository.SharedPreferencesRepository
import com.example.diplom.repository.SpecialistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: SpecialistRepository,
    val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {
    fun getUserIDD() = sharedPreferencesRepository.getUserId()
    fun getSpecIDD() = sharedPreferencesRepository.getSpecId()

    var review by mutableStateOf("")
        private set

    fun updateReview(input: String) {
        review = input
    }


    private val _specialistsList = mutableStateListOf<Specialist>()
    val specialistsistListState: List<Specialist> = _specialistsList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching


    fun handleEvent(mainScreenEvent: MainScreenEvent) {
        when (mainScreenEvent) {
            is MainScreenEvent.loadSpecialists -> {
                viewModelScope.launch {
                    val response = repository.getAllSpecialists()
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            _specialistsList.addAll(response.body() ?: emptyList())
                            Log.d("LoadServices", specialistsistListState.toString())
                        }
                    }
                }
            }

            is MainScreenEvent.searchQueryChanged -> {
                _searchQuery.value = mainScreenEvent.queryString
                viewModelScope.launch { searchSpecialists(_searchQuery.value) }
            }

            is MainScreenEvent.searchServices -> {
            }

            is MainScreenEvent.confirmButtonClicked -> {
                viewModelScope.launch {
                    val UserID = getUserIDD()
                    val SpecID = getSpecIDD()
                    val reviews = Review(
                        ReviewText = review,
                        Rating = "5",
                        SpecialistID = SpecID,
                        UserID = UserID
                    )
                    try {
                        val response = repository.makeReview(reviews)
                        if (response.isSuccessful) {
                            println("Отзыв успешно отправлен!")
                        } else {
                            println("Ошибка сервера: ${response.code()}")
                        }
                    } catch (e: Exception) {
                        println("Произошла ошибка: ${e.message}")
                    }
                }
            }

            is MainScreenEvent.hireButtonClicked -> {
                viewModelScope.launch {
                    val UserID = getUserIDD()
                    val SpecID = getSpecIDD()
                    val booking = Booking(
                        UserID = UserID,
                        SpecialistID = SpecID,
                        BookingDate = "13",
                        Status = "Ожидает подтверждения"
                    )
                    try {
                        val response = repository.createBooking(booking)
                        if (response.isSuccessful) {
                            response.body()
                        }
                    } catch (e: Exception) {
                        Log.e("ClientRegViewModel", "Exception in handleEvent", e)
                    }
                }
            }


            else -> {}
        }
    }

    private suspend fun searchSpecialists(query: String) {
        viewModelScope.launch {
            Log.d("searchEstablishments QUERY", "searchEstablishments: $query")
            var services = repository.searchSpecialists(name = query, skills = "")
            if (services.isNullOrEmpty()) {
                services = repository.searchSpecialists(name = "", skills = query)
            }
            if (services != null) {
                _specialistsList.clear()
                _specialistsList.addAll(services)
            }
        }
    }


    val reviewsListState: Flow<List<Review>> get() = _reviewsList
    private val _reviewsList = MutableStateFlow<List<Review>>(emptyList())

    fun getReviews(id: Int) {
        viewModelScope.launch {
            try {
                _reviewsList.value = repository.getReviews(SpecialistApi.ReviewRequest(id))
            } catch (e: Exception) {
                when (e) {
                    is IOException -> {
                        Log.e("MainScreenViewModel", "Network error: ${e.localizedMessage}")
                    }

                    is HttpException -> {
                        Log.e("MainScreenViewModel", "HTTP error: ${e.code()}")
                    }

                    else -> {
                        Log.e("MainScreenViewModel", "Unknown error: ${e.localizedMessage}")
                    }
                }
            }
        }
    }


}