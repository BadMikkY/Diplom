package com.example.diplom.screen.navigationBar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.diplom.R
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.diplom.navigation.Destination
import com.example.diplom.navigation.NavHost
import com.example.diplom.navigation.composable
import com.example.diplom.screen.bookingsScreen.BookingsScreen
import com.example.diplom.screen.main.MainScreen
import com.example.diplom.screen.settings.SettingsScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomBarMainScreen() {

    val screens = listOf(
        Destination.MainScreen,
        Destination.BookingsScreen,
        Destination.SettingsScreen
    )

    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(
                screens,
                bottomNavController
            )
        },
    ) {

        NavHost(
            navController = bottomNavController,
            startDestination = Destination.MainScreen
        ) {


            composable(destination = Destination.MainScreen) {
                MainScreen()
            }
            composable(destination = Destination.BookingsScreen) {
                BookingsScreen()
            }

            composable(destination = Destination.SettingsScreen) {
                SettingsScreen()
            }
        }
    }
}


@Composable
fun BottomBar(
    items: List<Destination.NoArgumentsDestinationBottom>,
    bottomNavController: NavHostController
) {


    NavigationBar(
        tonalElevation = 0.dp,
        containerColor = Color.White,
        modifier = Modifier
            .height(75.dp)
    ) {
        val navStackBackEntry by bottomNavController.currentBackStackEntryAsState()
        val currentDestination = navStackBackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.fullRoute } == true,

                onClick = {
                    bottomNavController.navigate(screen.fullRoute) {
                        popUpTo(bottomNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = screen.title),
                        fontSize = 12.sp,
                        )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = "icon"
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    indicatorColor = Color.White,
                    unselectedIconColor = colorResource(id = R.color.button_settings),
                    unselectedTextColor = colorResource(id = R.color.button_settings)
                )

            )
        }

    }

}

