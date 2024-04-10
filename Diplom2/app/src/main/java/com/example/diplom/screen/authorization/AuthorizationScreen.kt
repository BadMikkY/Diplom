package com.example.diplom.screen.authorization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.components.CommonButton
import com.example.diplom.components.TextField

@Composable
fun AuthorizationScreen(viewModel: AuthorizationViewModel = hiltViewModel()) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp)
            .padding(top = 80.dp)
    ) {

        Text(text = stringResource(id = R.string.auth))

        TextField(
            viewModel.email,
            R.string.email,
            R.string.email_example,
            16.dp,
            onChange = { email -> viewModel.updateEmail(email) },
            supportText = R.string.nothing,
            isError = false
        )

        TextField(
            viewModel.password,
            R.string.password,
            R.string.password,
            16.dp,
            onChange = { password -> viewModel.updatePassword(password) },
            supportText = R.string.nothing,
            isError = false
        )

        CommonButton(
            text = R.string.move_next,
            topPadding = 150.dp,
            onClick = { viewModel.handleEvent(AuthorizationEvent.ConfirmButtonClicked) })
    }
}


@Preview
@Composable
fun PreviewAuthorizationScreen() {
    AuthorizationScreen()
}