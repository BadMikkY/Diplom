package com.example.diplom.screen.workerSettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.diplom.model.Review
import com.example.diplom.screen.settings.ChangeLoginPasswordDialog
import com.example.diplom.screen.settings.SettingsEvent


val CustomColor = Color(100, 216, 230)

@Composable
fun WorkerSettingsScreen(viewModel: WorkerSettingsScreenViewModel = hiltViewModel()) {
    var showDialog by remember { mutableStateOf(false) }

    val reviewsList by viewModel.reviewsListState.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePicture()
            Row(
                modifier = Modifier
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = viewModel.getSpecName(),
                )
            }

            Text(
                text = "e-mail:"+viewModel.getSpecMail(),
                fontSize = 14.sp
            )
            Text(
                text = "опыт работы:"+viewModel.getSpecExp()+"лет",
                fontSize = 14.sp
            )
            Text(
                text = "Умения:"+viewModel.getSpecSkills(),
                fontSize = 14.sp
            )
            Text(
                text = "Рейтинг:"+viewModel.getSpecRates(),
                fontSize = 14.sp
            )

            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = com.example.diplom.screen.settings.CustomColor),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Сменить логин/пароль")
            }

            Text(
                text = "Reviews:",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp), // Set a fixed height to ensure the button is visible
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(reviewsList) { review ->
                    ReviewCard(review)
                }
            }
            CommonButton(
                onClick = { viewModel.handleEvent(WorkerSettingsEvent.LogOutButtonClicked) },
                containerColor = Color(100, 216, 230),
                text = R.string.log_out,
            )
            if (showDialog) {
                ChangeLoginPasswordWorkerDialog(
                    onDismiss = { showDialog = false },
                    onConfirm = { specname, password ->
                        viewModel.handleEvent(WorkerSettingsEvent.ChangeButtonClicked(specname,password))
                        showDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun ReviewCard(review: Review) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Rating: ${review.Rating}",
            fontSize = 14.sp
        )
        Text(
            text = "Review: ${review.ReviewText}",
            fontSize = 14.sp
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeLoginPasswordWorkerDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var specname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Сменить логин/пароль") },
        text = {
            Column {
                TextField(
                    value = specname,
                    onValueChange = { specname = it },
                    label = { Text(text = "Новый логин") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = com.example.diplom.screen.settings.CustomColor
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Новый пароль") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = com.example.diplom.screen.settings.CustomColor
                    )
                )
            }
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = { onConfirm(specname, password) },
                    colors = ButtonDefaults.buttonColors(containerColor = com.example.diplom.screen.settings.CustomColor)
                ) {
                    Text(text = "Сменить")
                }
            }
        },
        dismissButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = com.example.diplom.screen.settings.CustomColor)
                ) {
                    Text(text = "Отмена")
                }
            }
        }
    )
}

