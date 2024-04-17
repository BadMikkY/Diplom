package com.example.diplom.screen.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.navigation.Destination
import com.example.diplom.repository.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {
    fun getUserMail() = sharedPreferencesRepository.getUserMail()
    fun getUserName() = sharedPreferencesRepository.getUserName()
    var username by mutableStateOf("")
        private set

    fun handleEvent(settingsEvent: SettingsEvent) {
        when (settingsEvent) {
            is SettingsEvent.LogOutButtonClicked -> {
                viewModelScope.launch {
                    viewModelScope.launch(Dispatchers.IO) {
                        appNavigator.navigateTo(Destination.EntryScreen())
                    }
                }
            }
        }
    }
}