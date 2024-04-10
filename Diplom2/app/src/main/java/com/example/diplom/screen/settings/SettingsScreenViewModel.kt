package com.example.diplom.screen.settings

import androidx.lifecycle.ViewModel
import com.example.diplom.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
}