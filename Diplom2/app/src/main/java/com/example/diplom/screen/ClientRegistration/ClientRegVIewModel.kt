package com.example.diplom.screen.ClientRegistration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.screen.registration.RegistrationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel

class ClientRegVIewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    var onEmptyTextFields: (() -> Unit)? = null

    var userName by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun updateEmail(input: String) {
        email = input
    }

    fun updatePassword(input: String) {
        password = input
    }

    fun updateUserName(input: String) {
        userName = input
    }

    fun handleEvent(regEvent: ClientRegEvent) {
        when (regEvent) {
            is ClientRegEvent.SignInButtonClicked -> {

            }

            is ClientRegEvent.RegisterUser -> {

            }
        }
    }
}