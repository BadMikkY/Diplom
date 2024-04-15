package com.example.diplom.screen.entry

import androidx.lifecycle.ViewModel
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EntryScreenViewModel @Inject constructor(private val appNavigator: AppNavigator) :
    ViewModel() {
    fun handleEvent(entryEvent: EntryScreenEvent) {
        when (entryEvent) {
            is EntryScreenEvent.WorkerButtonClicked -> {
                appNavigator.tryNavigateTo(
                    Destination.RegistrationScreen(),
                    popUpToRoute = Destination.EntryScreen()
                )
            }

            is EntryScreenEvent.ClientButtonClicked -> {
                appNavigator.tryNavigateTo(
                    Destination.ClientRegScreen(),
                    popUpToRoute = Destination.EntryScreen()
                )
            }

            is EntryScreenEvent.AuthButtonClick -> {
                appNavigator.tryNavigateTo(
                    Destination.AuthorizationScreen(),
                    popUpToRoute = Destination.EntryScreen()
                )
            }
        }
    }


}