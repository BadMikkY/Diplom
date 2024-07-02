package com.example.diplom.screen.settings

import com.example.diplom.screen.authorization.AuthorizationEvent

sealed class SettingsEvent {


        object LogOutButtonClicked : SettingsEvent()
        data class ChangeButtonClicked(val username: String, val password: String) : SettingsEvent()

}