package com.example.diplom.screen.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.navigation.Destination
import com.example.diplom.repository.UserRepository
import com.example.diplom.screen.registration.RegistrationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {

    var skills by mutableStateOf("")
        private set
    fun updateSkills(input: String) {
        skills = input
    }

    var experience by mutableStateOf("")
        private set
    fun updateExperience(input: String) {
        experience = input
    }

    var email by mutableStateOf("")
        private set

    fun updateEmail(input: String) {
        email = input
    }

    var specName by mutableStateOf("")
        private set

    fun updateName(input: String) {
        specName = input
    }

    var password by mutableStateOf("")
        private set

    fun updatePassword(input: String) {
        password = input
    }

    var confirmPassword by mutableStateOf("")
        private set

    var onEmptyTextFields: (() -> Unit)? = null

    fun handleEvent(regEvent: RegistrationEvent) {

        when (regEvent) {
            is RegistrationEvent.SignInButtonClicked -> {
                appNavigator.tryNavigateTo(
                    Destination.AuthorizationScreen(),
                    popUpToRoute = Destination.RegistrationScreen()
                )
            }

            is RegistrationEvent.RegisterUser -> {

            }
        }
    }


    private fun isTextFieldsEmpty(): Boolean {
        return if (email.isNotEmpty() || password.isNotEmpty() || confirmPassword.isNotEmpty()) {
            true
        } else {
            onEmptyTextFields
            false
        }
    }
}