package com.example.diplom.screen.workerSettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.components.CommonButton
import com.example.diplom.components.ProfilePicture
import com.example.diplom.navigation.Destination
import com.example.diplom.screen.settings.SettingsScreenViewModel

@Composable
fun WorkerSettingsScreen(viewModel: WorkerSettingsScreenViewModel = hiltViewModel()) {

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
                    text = viewModel.getSpecName(),
                )
                Spacer(modifier = Modifier.size(7.dp))
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = viewModel.getSpecMail(),
                fontSize = 14.sp
            )
            Text(
                text = viewModel.getSpecExp(),
                fontSize = 14.sp
            )
            Text(
                text = viewModel.getSpecSkills(),
                fontSize = 14.sp
            )
            Text(
                text = viewModel.getSpecRates(),
                fontSize = 14.sp
            )
            Text(
                text = "specreviews",
                fontSize = 14.sp
            )
            Row(
                modifier = Modifier
                    .padding(top = 22.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp)
            ) {
            }
        }
        Spacer(modifier = Modifier.size(300.dp))
        CommonButton(onClick = {viewModel.handleEvent(WorkerSettingsEvent.LogOutButtonClicked)} ,text = R.string.log_out)
    }

}