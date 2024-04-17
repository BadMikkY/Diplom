package com.example.diplom.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import com.example.diplom.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.components.CommonButton
import com.example.diplom.components.ProfilePicture

@Composable
fun SettingsScreen(viewModel: SettingsScreenViewModel = hiltViewModel()) {

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
//                IconButton(onClick = {
//                    viewModel.onChangeClick()
//                }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_edit),
//                        contentDescription = "edit"
//                    )
//                }
//                if (viewModel.isDiaologShown) {
//                    PopUpToChange(
//                        onDismiss = { viewModel.onCancelClick() },
//                        onConfirm = {viewModel.handleEvent(SettingsEvent.ConfirmButtonClick) }) {
//                    }
//                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = viewModel.getUserMail(),
                fontSize = 14.sp
            )

            Row(
                modifier = Modifier
                    .padding(top = 22.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp)
            ) {
            }
        }
        Spacer(modifier = Modifier.size(400.dp))
        CommonButton(onClick = {viewModel.handleEvent(SettingsEvent.LogOutButtonClicked)} ,text = R.string.log_out)
    }

}