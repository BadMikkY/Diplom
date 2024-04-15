package com.example.diplom.screen.workerMain

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.components.SearchTextField
import com.example.diplom.screen.main.MainScreenViewModel

@Composable
fun WorkerMainScreen(viewModel: MainScreenViewModel = hiltViewModel()){
    Column {
        SearchTextField()
    }
}