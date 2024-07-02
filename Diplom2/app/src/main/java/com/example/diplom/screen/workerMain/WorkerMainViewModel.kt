package com.example.diplom.screen.workerMain

import android.health.connect.datatypes.BloodGlucoseRecord.SpecimenSource
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.model.Booking
import com.example.diplom.model.Service
import com.example.diplom.model.Specialist
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.repository.ServicesRepository
import com.example.diplom.repository.SharedPreferencesRepository
import com.example.diplom.repository.SpecialistRepository
import com.example.diplom.screen.main.MainScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WorkerMainViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: ServicesRepository,
    private val repa: SpecialistRepository,
    val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    fun getUserIDD() = sharedPreferencesRepository.getUserId()
    fun getSpecIDD() = sharedPreferencesRepository.getSpecId()

    private val _servicesList = mutableStateListOf<Service>()
    val serviceistListState: List<Service> = _servicesList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching


    fun handleEvent(workerMainEvent: WorkerMainEvent) {
        when (workerMainEvent) {
            is WorkerMainEvent.loadServices -> {
                viewModelScope.launch {
                    val response = repository.getAllServices()
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            _servicesList.addAll(response.body() ?: emptyList())
                            Log.d("LoadServices", serviceistListState.toString())
                        }
                    }
                }
            }

            is WorkerMainEvent.searchQueryChanged -> {
                _searchQuery.value = workerMainEvent.queryString
                viewModelScope.launch { searchServices(_searchQuery.value) }
            }

            is WorkerMainEvent.confirmButtonClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val UserID = getUserIDD()
                    val SpecID = getSpecIDD()

                    val booking = Booking(UserID = UserID, SpecialistID = SpecID, BookingDate = "5", Status = "Ожидает подтверждения")

                    try {
                        val response = repa.createBooking(booking)
                        if(response.isSuccessful){
                            response.body()
                        }
                    } catch (e: Exception) {
                        Log.e("ClientRegViewModel", "Exception in handleEvent", e)
                    }
                }
            }
        }
    }

    private suspend fun searchServices(query: String) {
        viewModelScope.launch {
            Log.d("searchEstablishments QUERY", "searchEstablishments: $query")
            var services = repository.searchServices(name = query, description = "")
            if (services.isNullOrEmpty()) {
                services = repository.searchServices(name = "", description = query)
            }
            if (services != null) {
                _servicesList.clear()
                _servicesList.addAll(services)
            }
        }
    }
}