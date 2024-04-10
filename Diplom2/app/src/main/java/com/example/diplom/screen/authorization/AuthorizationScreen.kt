package com.example.diplom.screen.authorization

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthorizationScreen( viewModel: AuthorizationViewModel = hiltViewModel()

){
    Text(text = "dsada")
}


@Preview
@Composable
fun PreviewAuthorizationScreen() {
    AuthorizationScreen()
}