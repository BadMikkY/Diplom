package com.example.diplom.screen.authorization

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {

    var onEmptyTextFields: (() -> Unit)? = null

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun updateEmail(input: String){
        email = input
    }

    fun updatePassword(input:String){
        password = input
    }

    fun handleEvent(authEvent: AuthorizationEvent) {
        when (authEvent) {
            is AuthorizationEvent.ConfirmButtonClicked -> {
                appNavigator.tryNavigateTo(
                    Destination.NavigationBarScreen(),
                    popUpToRoute = Destination.NavigationBarScreen()
                )
            }
        }
    }
}