package com.example.diplom.screen.registration

sealed class RegistrationEvent {
    object SignInButtonClicked :
        RegistrationEvent()

    object RegisterUser :
        RegistrationEvent()

}