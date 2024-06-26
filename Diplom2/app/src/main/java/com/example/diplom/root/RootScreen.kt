package com.example.diplom.root

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.diplom.navigation.Destination
import com.example.diplom.navigation.NavigationIntent
import com.example.diplom.navigation.composable
import com.example.diplom.screen.ClientRegistration.ClientRegScreen
import com.example.diplom.screen.authorization.AuthorizationScreen
import com.example.diplom.screen.bookingsScreen.BookingsScreen
import com.example.diplom.screen.entry.EntryScreen
import com.example.diplom.screen.main.MainScreen
import com.example.diplom.screen.navigationBar.BottomBarMainScreen
import com.example.diplom.screen.registration.RegistrationScreen
import com.example.diplom.screen.settings.SettingsScreen
import com.example.diplom.screen.workerNavigationBar.BottomWorkerBarScreen

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun RootSreen(
    rootViewModel: RootViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavigationEffects(
        navigationChannel = rootViewModel.navigationChannel,
        navHostController = navController
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        com.example.diplom.navigation.NavHost(
            navController = navController,
            startDestination = Destination.AuthorizationScreen
        ) {
            composable(destination = Destination.RegistrationScreen) {
                RegistrationScreen()
            }

            composable(destination = Destination.BottomWorkerBarScreen){
                BottomWorkerBarScreen()
            }

            composable(destination = Destination.AuthorizationScreen) {
                AuthorizationScreen()
            }
            composable(destination = Destination.EntryScreen) {
                EntryScreen()
            }
            composable(destination = Destination.ClientRegScreen) {
                ClientRegScreen()
            }
            composable(destination = Destination.MainScreen) {
                MainScreen()
            }
            composable(destination = Destination.BookingsScreen) {
                BookingsScreen()
            }
            composable(destination = Destination.SettingsScreen) {
                SettingsScreen()
            }

            composable(destination = Destination.NavigationBarScreen) {
                BottomBarMainScreen()
            }
        }
    }
}


@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }

                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}