package com.example.diplom.root

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.diplom.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    appNavigator: AppNavigator
) : ViewModel() {
    val navigationChannel = appNavigator.navigationChannel
}