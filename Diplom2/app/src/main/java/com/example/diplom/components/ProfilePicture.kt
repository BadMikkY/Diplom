package com.example.diplom.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diplom.R

@Preview
@Composable
fun ProfilePicture() {

    var showDialog by remember { mutableStateOf(false) }
    if(showDialog){
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.Gray)
            .clickable {
                //Сделать тут
                showDialog = true
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_account_circle_24),
            contentDescription = "User Icon",
            tint = Color.White,
            modifier = Modifier.size(50.dp)
        )
    }
}