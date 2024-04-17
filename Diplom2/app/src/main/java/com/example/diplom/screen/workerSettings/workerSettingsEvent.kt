package com.example.diplom.screen.workerSettings

import com.example.diplom.screen.settings.SettingsEvent

sealed class WorkerSettingsEvent {
    object LogOutButtonClicked :
        WorkerSettingsEvent()
}