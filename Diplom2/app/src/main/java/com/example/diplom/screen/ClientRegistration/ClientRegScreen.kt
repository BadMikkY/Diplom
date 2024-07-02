package com.example.diplom.screen.ClientRegistration

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.components.CommonButton
import com.example.diplom.components.TextField
import com.example.diplom.screen.registration.RegistrationEvent

@Composable
fun ClientRegScreen(vIewModel: ClientRegVIewModel = hiltViewModel()) {
    val context = LocalContext.current

    vIewModel.onEmptyTextFields = {
        Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 26.dp)
        .padding(top = 80.dp)) {
        Text(text = stringResource(id = R.string.welcome))

        TextField(
            vIewModel.UserName,
            R.string.your_name,
            R.string.your_name,
            16.dp,
            onChange = { name -> vIewModel.updateUserName(name) },
            supportText = R.string.nothing,
            isError = false
        )

        TextField(
            vIewModel.Email,
            R.string.email, R.string.email_example, 16.dp, isVerified = true,
            onChange = { email -> vIewModel.updateEmail(email) },
            supportText = R.string.email_valid,
        )

        TextField(
            vIewModel.Password,
            R.string.password, R.string.password, 16.dp, isVerified = true,
            onChange = { password -> vIewModel.updatePassword(password) },
            supportText = R.string.nothing
        )

        CommonButton(
            text = R.string.move_next,
            topPadding = 150.dp,
            onClick = { vIewModel.handleEvent(ClientRegEvent.RegisterUser) }
        )
    }
}