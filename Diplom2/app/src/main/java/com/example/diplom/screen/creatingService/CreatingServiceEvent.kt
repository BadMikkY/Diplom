package com.example.diplom.screen.creatingService

import com.example.diplom.screen.authorization.AuthorizationEvent

sealed class CreatingServiceEvent {
    object ConfirmButtonClicked :
        CreatingServiceEvent()
}