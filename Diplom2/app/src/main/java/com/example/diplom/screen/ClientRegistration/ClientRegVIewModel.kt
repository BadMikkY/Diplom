package com.example.diplom.screen.ClientRegistration

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.model.User
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.navigation.Destination
import com.example.diplom.repository.SharedPreferencesRepository
import com.example.diplom.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClientRegVIewModel @Inject constructor(
    private val reposity: UserRepository,
    private val sharedPreferencesRepisitory: SharedPreferencesRepository,
    private val appNavigator: AppNavigator
) : ViewModel() {
    var onEmptyTextFields: (() -> Unit)? = null

    var UserName by mutableStateOf("")
        private set

    var Email by mutableStateOf("")
        private set

    var Password by mutableStateOf("")
        private set

    fun updateEmail(input: String) {
        Email = input
    }

    fun updatePassword(input: String) {
        Password = input
    }

    fun updateUserName(input: String) {
        UserName = input
    }

    fun handleEvent(regEvent: ClientRegEvent) {
        when (regEvent) {
            is ClientRegEvent.RegisterUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val response = reposity.registerUser(User(UserName, Password, Email, Role = "user"))
                        if (response.isSuccessful && isTextFieldsEmpty()) {
                            response.body()?.let { userResponse ->
                                userResponse.Username?.let { sharedPreferencesRepisitory.setUsername(it) }
                                userResponse.Email?.let { sharedPreferencesRepisitory.setUserMail(it) }
                                sharedPreferencesRepisitory.setUserId(userResponse.userID)
                            }
                            viewModelScope.launch {
                                appNavigator.tryNavigateTo(
                                    Destination.AuthorizationScreen(),
                                    popUpToRoute = Destination.ClientRegScreen()
                                )
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("ClientRegViewModel", "Exception in handleEvent", e)
                    }
                }
            }

            is ClientRegEvent.EmailStringChanged -> {

            }

            is ClientRegEvent.NameStringChanged -> {

            }

            is ClientRegEvent.PasswordStringChanged -> {

            }
        }
    }


    private fun isTextFieldsEmpty(): Boolean {
        return if (Email.isNotEmpty() || Password.isNotEmpty()) {
            true
        } else {
            onEmptyTextFields
            false
        }
    }
}