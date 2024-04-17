package com.example.diplom.screen.workerSettings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.navigation.Destination
import com.example.diplom.repository.SharedPreferencesRepository
import com.example.diplom.screen.settings.SettingsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerSettingsScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {
    fun getSpecMail() = sharedPreferencesRepository.getSpecialistMail()
    fun getSpecName() = sharedPreferencesRepository.getSpecName()
    fun getSpecExp() = sharedPreferencesRepository.getSpecExp()
    fun getSpecRates() = sharedPreferencesRepository.getSpecRates()
    fun getSpecSkills() = sharedPreferencesRepository.getSpecSkills()

    fun handleEvent(workerSettingsEvent: WorkerSettingsEvent) {
        when (workerSettingsEvent) {
            is WorkerSettingsEvent.LogOutButtonClicked -> {
                viewModelScope.launch {
                    viewModelScope.launch(Dispatchers.IO) {
                        appNavigator.navigateTo(Destination.EntryScreen())
                    }
                }
            }
        }
    }
}