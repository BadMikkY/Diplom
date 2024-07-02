package com.example.diplom.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.components.CommonButton
import com.example.diplom.components.ProfilePicture

val CustomColor = Color(100, 216, 230)

@Composable
fun SettingsScreen(viewModel: SettingsScreenViewModel = hiltViewModel()) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = 26.dp)
            .padding(top = 48.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePicture()
            Row(
                modifier = Modifier
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = viewModel.getUserName(),
                )
                Spacer(modifier = Modifier.size(7.dp))
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = viewModel.getUserMail(),
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = CustomColor),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Сменить логин/пароль")
        }
        Spacer(modifier = Modifier.size(350.dp))
        CommonButton(
            onClick = { viewModel.handleEvent(SettingsEvent.LogOutButtonClicked) },
            text = R.string.log_out,
            containerColor = CustomColor
        )

        if (showDialog) {
            ChangeLoginPasswordDialog(
                onDismiss = { showDialog = false },
                onConfirm = { username, password ->
                    viewModel.handleEvent(SettingsEvent.ChangeButtonClicked(username, password))
                    showDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeLoginPasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Сменить логин/пароль") },
        text = {
            Column {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = "Новый логин") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = CustomColor
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Новый пароль") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = CustomColor
                    )
                )
            }
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = { onConfirm(username, password) },
                    colors = ButtonDefaults.buttonColors(containerColor = CustomColor)
                ) {
                    Text(text = "Сменить")
                }
            }
        },
        dismissButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = CustomColor)
                ) {
                    Text(text = "Отмена")
                }
            }
        }
    )
}
