package com.example.diplom.screen.entry

import com.example.diplom.screen.registration.RegistrationEvent

sealed class EntryScreenEvent {
    object ClientButtonClicked :
        EntryScreenEvent()

    object  WorkerButtonClicked:
        EntryScreenEvent()
}