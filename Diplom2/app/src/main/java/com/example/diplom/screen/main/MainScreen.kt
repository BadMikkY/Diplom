package com.example.diplom.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.components.SearchTextField

@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()){
    Column {
        SearchTextField()
    }
}