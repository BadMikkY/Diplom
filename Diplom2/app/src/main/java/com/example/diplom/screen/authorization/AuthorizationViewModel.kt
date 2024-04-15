package com.example.diplom.screen.authorization

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.model.Specialist
import com.example.diplom.model.User
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.navigation.Destination
import com.example.diplom.repository.SharedPreferencesRepository
import com.example.diplom.repository.SpecialistRepository
import com.example.diplom.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


    @HiltViewModel
    class AuthorizationViewModel @Inject constructor(
        private val reposity: UserRepository,
        private val specialistRepository: SpecialistRepository,
        private val sharedPreferencesRepisitory: SharedPreferencesRepository,
        private val appNavigator: AppNavigator
    ) : ViewModel() {

        var onEmptyTextFields: (() -> Unit)? = null

        var email by mutableStateOf("")
            private set


        var error by mutableStateOf("")

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
                    viewModelScope.launch(Dispatchers.IO) {
                        try {
                            val specialist = Specialist(Email = email, Password = password)
                            val responseSpec = specialistRepository.loginSpec(specialist)
                            if (responseSpec.isSuccessful) {
                                responseSpec.body()?.let { specialistResponse ->
                                    specialistResponse.SpecName?.let { sharedPreferencesRepisitory.setSpecName(it) }
                                    specialistResponse.Email?.let { sharedPreferencesRepisitory.setSpecialistMail(it) }
                                    sharedPreferencesRepisitory.setSpecId(specialistResponse.specID)
                                }
                                viewModelScope.launch {
                                    appNavigator.tryNavigateTo(
                                        Destination.BottomWorkerBarScreen(),
                                        popUpToRoute = Destination.AuthorizationScreen()
                                    )
                                }
                            } else {
                                val user = User(Email = email, Password = password)
                                val responseUser = reposity.loginUser(user)
                                if (responseUser.isSuccessful) {
                                    responseUser.body()?.let { userResponse ->
                                        userResponse.Username?.let { sharedPreferencesRepisitory.setUsername(it) }
                                        userResponse.Email?.let { sharedPreferencesRepisitory.setUserMail(it) }
                                        sharedPreferencesRepisitory.setUserId(userResponse.userID)
                                    }
                                    viewModelScope.launch {
                                        appNavigator.tryNavigateTo(
                                            Destination.NavigationBarScreen(),
                                            popUpToRoute = Destination.AuthorizationScreen()
                                        )
                                    }
                                } else {
                                    error = "Ошибка авторизации. Пожалуйста, проверьте введенные данные."
                                }
                            }
                        } catch (e: Exception) {
                            error = "Произошла ошибка: ${e.message}"
                        }
                    }
                }
            }
        }

    }