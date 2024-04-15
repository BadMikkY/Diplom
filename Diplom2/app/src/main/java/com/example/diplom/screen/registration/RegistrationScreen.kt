package com.example.diplom.screen.registration


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.components.CommonButton
import com.example.diplom.components.TextField


@Composable
fun RegistrationScreen(viewModel: RegistrationViewModel = hiltViewModel()) {

    val context = LocalContext.current


    viewModel.onEmptyTextFields =
        { Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show() }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp)
            .padding(top = 80.dp)
    ) {
        Text(text = stringResource(id = R.string.welcome))

        TextField(
            viewModel.SpecName,
            R.string.your_name,
            R.string.your_name,
            16.dp,
            onChange = { name -> viewModel.updateName(name) },
            supportText = R.string.nothing,
            isError = false
        )

        TextField(
            viewModel.Email,
            R.string.email, R.string.email_example, 16.dp, isVerified = true,
            onChange = { email -> viewModel.updateEmail(email) },
            supportText = R.string.email_valid,
        )

        TextField(
            viewModel.Password,
            R.string.password,R.string.password,16.dp, isVerified = true,
            onChange = {password -> viewModel.updatePassword(password)},
            supportText = R.string.nothing
        )

        TextField(
            viewModel.Skills,
            R.string.skills,R.string.skills,16.dp, isVerified = true,
            onChange = {skills -> viewModel.updateSkills(skills)},
            supportText = R.string.nothing
        )

        TextField(
            viewModel.Experience,
            R.string.experience,R.string.experience,16.dp, isVerified = true,
            onChange = {experience -> viewModel.updateExperience(experience)},
            supportText = R.string.nothing
        )



        CommonButton(
            text = R.string.move_next,
            onClick = { viewModel.handleEvent(RegistrationEvent.SignInButtonClicked) })
    }
}