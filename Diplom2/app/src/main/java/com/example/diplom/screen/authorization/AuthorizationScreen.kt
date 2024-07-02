package com.example.diplom.screen.authorization

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.components.CommonButton
import com.example.diplom.components.TextField

@Composable
fun AuthorizationScreen(viewModel: AuthorizationViewModel = hiltViewModel()) {
    val context = LocalContext.current

    viewModel.onEmptyTextFields = {
        Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
    }

    Box {
        Image(
            painter = painterResource(id = R.drawable.auth),
            contentDescription = "im1",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.auth),
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            )

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
                text = viewModel.password,
                labelText = R.string.password,
                textFieldText = R.string.password,
                textFieldBottomPadding = 16.dp,
                onChange = { password -> viewModel.updatePassword(password) },
                supportText = R.string.nothing,
                isError = false,
                isPasswordField = true
            )

            Text(
                text = "Хотите зарегистрироваться как наниматель?",
                modifier = Modifier.padding(top = 25.dp).clickable { viewModel.handleEvent(AuthorizationEvent.ClientRegistrationClicked) }
            )
            Text(
                text = "Хотите зарегистрироваться как работник?",
                modifier = Modifier.clickable { viewModel.handleEvent(AuthorizationEvent.WorkerRegistrationClicked) }
            )

            CommonButton(
                text = R.string.move_next,
                topPadding = 190.dp,
                containerColor = Color(100, 216, 230),
                borderColor = Color.Red,
                onClick = { viewModel.handleEvent(AuthorizationEvent.ConfirmButtonClicked) }
            )
        }
    }
}

@Preview
@Composable
fun PreviewAuthorizationScreen() {
    AuthorizationScreen()
}