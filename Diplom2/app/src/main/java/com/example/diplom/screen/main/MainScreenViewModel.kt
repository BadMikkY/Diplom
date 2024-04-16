package com.example.diplom.screen.main

import android.health.connect.datatypes.BloodGlucoseRecord.SpecimenSource
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.model.Service
import com.example.diplom.model.ServiceResponse
import com.example.diplom.model.Specialist
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.repository.ServicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: ServicesRepository
) : ViewModel() {


    private val _servicesList = mutableStateListOf<Service>()
    val serviceistListState: List<Service> = _servicesList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching


    fun handleEvent(mainScreenEvent: MainScreenEvent) {
        when (mainScreenEvent) {
            is MainScreenEvent.loadServices -> {
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

            is MainScreenEvent.searchQueryChanged -> {
                _searchQuery.value = mainScreenEvent.queryString
                viewModelScope.launch { searchServices(_searchQuery.value) }
            }

            is MainScreenEvent.searchServices -> {
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