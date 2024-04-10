package com.example.diplom.screen.main

import android.health.connect.datatypes.BloodGlucoseRecord.SpecimenSource
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.model.Specialist
import com.example.diplom.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {



    private var page = 0
    private var size = 10

    private val _specialistsList = mutableStateListOf<Specialist>()
    val establishmentsListState: List<Specialist> = _specialistsList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = repository.getEstablishments(page, size)
//            if (response.isSuccessful) {
//                withContext(Dispatchers.Main) {
//                    _establishmentsList.addAll(response.body() ?: emptyList())
//                }
//
//            }
//        }
//    }


    fun handleEvent(mainScreenEvent: MainScreenEvent) {
        when (mainScreenEvent) {
            is MainScreenEvent.loadMoreEstablishments -> {
                viewModelScope.launch {
//                    page++
//                    val response = repository.getEstablishments(page, size)
//                    if (response.isSuccessful) {
//                        withContext(Dispatchers.Main) {
//                            _establishmentsList.addAll(response.body() ?: emptyList())
//                            Log.d("LoadEstabloshments", establishmentsListState.toString())
//                        }
//                    }
                }
            }

            is MainScreenEvent.searchQueryChanged -> {
                _searchQuery.value = mainScreenEvent.queryString
                viewModelScope.launch {
                    searchEstablishments(_searchQuery.value)
                }
            }

            is MainScreenEvent.searchEstablishments -> {
//                viewModelScope.launch {
//                    searchEstablishments(_searchQuery.value)
//                }
            }
        }
    }
    private suspend fun searchEstablishments(query: String) {
        viewModelScope.launch {
//            Log.d("searchEstablishments QUERY", "searchEstablishments: $query")
//            val establishments = repository.searchEstablishments(name = query, 0, 10)
//            if (establishments != null) {
//                _establishmentsList.clear()
//                _establishmentsList.addAll(establishments)
//            }
        }
    }
}