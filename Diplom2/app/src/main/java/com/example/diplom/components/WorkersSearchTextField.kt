package com.example.diplom.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.screen.main.MainScreenEvent
import com.example.diplom.screen.main.MainScreenViewModel
import com.example.diplom.screen.workerMain.WorkerMainEvent
import com.example.diplom.screen.workerMain.WorkerMainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkersSearchTextField(
    viewModel: WorkerMainViewModel = hiltViewModel(),
) {
    var text by rememberSaveable { mutableStateOf("") }
    var hasFocus by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text, onValueChange = {
            text = it.trim()
            viewModel.handleEvent(WorkerMainEvent.searchQueryChanged(it))
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.worker),
                textAlign = TextAlign.Center
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
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
            cursorColor = Color.Black
        )
    )
}