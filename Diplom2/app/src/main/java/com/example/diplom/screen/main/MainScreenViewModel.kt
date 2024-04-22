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
import com.example.diplom.repository.SpecialistRepository
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
    private val repository: SpecialistRepository
) : ViewModel() {


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

}