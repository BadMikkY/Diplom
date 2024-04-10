package com.example.diplom.screen.ClientRegistration

import com.example.diplom.screen.registration.RegistrationEvent

sealed class ClientRegEvent {
    object SignInButtonClicked :
        ClientRegEvent()

    object RegisterUser :
        ClientRegEvent()

//    class PasswordStringChanged():
//        ClientRegEvent()
//    class EmailStringChanged():
//        ClientRegEvent()
//
//    class NameStringChanged():
//        ClientRegEvent()
}