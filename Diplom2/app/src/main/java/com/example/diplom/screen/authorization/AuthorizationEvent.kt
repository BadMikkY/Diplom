package com.example.diplom.screen.authorization

sealed class AuthorizationEvent {

    object ConfirmButtonClicked :
        AuthorizationEvent()
}