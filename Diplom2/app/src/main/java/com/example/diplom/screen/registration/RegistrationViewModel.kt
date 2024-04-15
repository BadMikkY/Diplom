package com.example.diplom.screen.registration

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.api.SpecialistApi
import com.example.diplom.model.Specialist
import com.example.diplom.model.SpecialistResponce
import com.example.diplom.model.User
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.navigation.Destination
import com.example.diplom.repository.SharedPreferencesRepository
import com.example.diplom.repository.UserRepository
import com.example.diplom.screen.registration.RegistrationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val reposity: SpecialistApi,
    private val sharedPreferencesRepisitory: SharedPreferencesRepository,
    private val appNavigator: AppNavigator
) : ViewModel() {

    var Skills by mutableStateOf("")
        private set
    fun updateSkills(input: String) {
        Skills = input
    }

    var Experience by mutableStateOf("")
        private set
    fun updateExperience(input: String) {
        Experience = input
    }

    var Email by mutableStateOf("")
        private set

    fun updateEmail(input: String) {
        Email = input
    }

    var SpecName by mutableStateOf("")
        private set

    fun updateName(input: String) {
        SpecName = input
    }

    var Password by mutableStateOf("")
        private set

    fun updatePassword(input: String) {
        Password = input
    }

    var confirmPassword by mutableStateOf("")
        private set

    var onEmptyTextFields: (() -> Unit)? = null

    fun handleEvent(regEvent: RegistrationEvent) {


        when (regEvent) {
            is RegistrationEvent.SignInButtonClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val response = reposity.registerSpec(Specialist(Email,Skills,SpecName,Password,Experience, Schedule = "", Rates = "5"))
                        if (response.isSuccessful && isTextFieldsEmpty()) {
                            response.body()?.let { specialistResponse ->
                                specialistResponse.Email?.let { sharedPreferencesRepisitory.setSpecialistMail(it) }
                                specialistResponse.Rates?.let { sharedPreferencesRepisitory.setSpecRates(it) }
                                specialistResponse.Skills?.let { sharedPreferencesRepisitory.setSpecSkills(it) }
                                specialistResponse.SpecName?.let { sharedPreferencesRepisitory.setSpecName(it) }
                                specialistResponse.Experience?.let { sharedPreferencesRepisitory.setSpecExp(it) }
                                specialistResponse.Schedule?.let { sharedPreferencesRepisitory.setShedule(it) }
                                sharedPreferencesRepisitory.setUserId(specialistResponse.specID)
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
        }
    }


    private fun isTextFieldsEmpty(): Boolean {
        return if (Email.isNotEmpty() || Password.isNotEmpty() || confirmPassword.isNotEmpty()) {
            true
        } else {
            onEmptyTextFields
            false
        }
    }
}