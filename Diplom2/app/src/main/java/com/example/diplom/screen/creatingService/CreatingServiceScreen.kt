package com.example.diplom.screen.creatingService

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.components.CommonButton
import com.example.diplom.components.TextField
import com.example.diplom.screen.authorization.AuthorizationEvent
import com.example.diplom.screen.authorization.AuthorizationViewModel

@Composable
fun CreatingServiceScreen(viewModel: CreatingServiceViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp)
            .padding(top = 80.dp)
    ) {
        Text(text = stringResource(id = R.string.creating_service_))

        TextField(
            viewModel.ServiceName,
            R.string.description,
            R.string.description,
            16.dp,
            onChange = { serviceName -> viewModel.updateServiceName(serviceName) },
            supportText = R.string.nothing,
            isError = false
        )

        TextField(
            viewModel.Description,
            R.string.servicename, R.string.servicename,
            16.dp,
            onChange = { description -> viewModel.updateDescription(description) },
            supportText = R.string.nothing,
            isError = false
        )

        CommonButton(
            text = R.string.confirm,
            topPadding = 150.dp,
            onClick = { viewModel.handleEvent(CreatingServiceEvent.ConfirmButtonClicked) })
    }
}