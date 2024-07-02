package com.example.diplom.screen.entry

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.components.CommonButton
import com.example.diplom.components.EntryButton


@Composable
fun EntryScreen(viewModel: EntryScreenViewModel = hiltViewModel()) {
    val Context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp)
            .padding(top = 100.dp)
    ) {
        Text(
            text = stringResource(id = R.string.entry),
            style = TextStyle(
                fontSize = 24.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 350.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EntryButton(
                text = R.string.worker,
                buttonWidth = 130.dp,
                containerColor = Color(173, 216, 230),
                borderColor = Color.Cyan,
                onClick = { viewModel.handleEvent(EntryScreenEvent.WorkerButtonClicked) }
            )
            EntryButton(
                text = R.string.client,
                buttonWidth = 130.dp,
                containerColor = Color(173, 216, 230),
                borderColor = Color.Blue,
                onClick = { viewModel.handleEvent(EntryScreenEvent.ClientButtonClicked) })
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            EntryButton(
                text = R.string.Auth,
                buttonWidth = 190.dp,
                containerColor = Color(173, 216, 230),
                borderColor = Color.Red,
                onClick = { viewModel.handleEvent(EntryScreenEvent.AuthButtonClick) })
        }
    }
}