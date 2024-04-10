package com.example.diplom.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(viewModel: SettingsScreenViewModel = hiltViewModel()) {
    Column {
        Text(text = "hiiii")
    }
}