package com.example.diplom.screen.workerMain

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.model.Specialist
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.screen.main.MainScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerMainViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
}