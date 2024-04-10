package com.example.diplom.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.diplom.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    text: String,
    labelText: Int,
    textFieldText: Int,
    textFieldBottomPadding: Dp,
    isPasswordField: Boolean = false,
    isVerified: Boolean = false,
    onChange: (String) -> Unit = {},
    supportText: Int,
    isError: Boolean = false
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var hasFocus by remember { mutableStateOf(false) }

    Text(
        text = stringResource(id = labelText),
        modifier = Modifier.padding(bottom = 6.dp)
    )

    OutlinedTextField(
        value = text,
        onValueChange = {
            onChange(it)
        },
        isError = isError,
        placeholder = {
            Text(text = stringResource(id = textFieldText))
        },
        supportingText = {
            if (isError) {
                Text(text = stringResource(id = supportText))
            }

        },
        visualTransformation = if (passwordVisible || !isPasswordField) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (isPasswordField) {

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = 0),
                        contentDescription = ""
                    )
                }
            }
        },
        modifier = Modifier
            .padding(bottom = textFieldBottomPadding)
            .fillMaxWidth()
            .height(70.dp)
            .border(
                shape = RoundedCornerShape(10.dp),
                width = 1.dp,
                color = if (hasFocus) Color.Black else Color.LightGray
            )
            .onFocusChanged { focusState -> hasFocus = focusState.hasFocus },
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Unspecified,
            unfocusedBorderColor = Color.Unspecified,
            cursorColor = Color.Black,
        ),
    )
}
