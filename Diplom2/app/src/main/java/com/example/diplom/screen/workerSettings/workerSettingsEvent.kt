package com.example.diplom.screen.workerSettings

import com.example.diplom.screen.settings.SettingsEvent
import com.example.diplom.screen.workerBookings.WorkerBookingsEvent

sealed class WorkerSettingsEvent {
    object LogOutButtonClicked : WorkerSettingsEvent()
    data class ChangeButtonClicked(val specname: String, val password: String) : WorkerSettingsEvent()
}