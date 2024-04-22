package com.example.diplom.screen.main

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.components.ProfilePicture
import com.example.diplom.components.SearchTextField
import com.example.diplom.model.Service
import com.example.diplom.model.Specialist

@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(MainScreenEvent.loadSpecialists)
    }

    val specState = viewModel.specialistsistListState

    Column {
        SearchTextField(viewModel)

        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 22.dp)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = Color.Black
        )

        CardGrid(
            specialists = specState,
            onEndReached = { viewModel.handleEvent(MainScreenEvent.loadSpecialists) })
    }
}


@Composable
fun CardGrid(
    specialists: List<Specialist>,
    viewModel: MainScreenViewModel = hiltViewModel(),
    onEndReached: () -> Unit
) {
    val lazyListState = rememberLazyStaggeredGridState()
    // Добавляем состояние для отслеживания выбранного специалиста
    var selectedSpecialist by remember { mutableStateOf<Specialist?>(null) }

    LazyVerticalStaggeredGrid(
        state = lazyListState,
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 2.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 75.dp)
    ) {

        items(specialists) {
            Log.d("WWWWWWWWWWWWWWWWW", "CardGrid: " + it)
            if (specialists.indexOf(it) == specialists.size - 1) {
                Log.d("End", specialists.toString())
                onEndReached()
                Log.d("AfterEnd", specialists.toString())
            }

            // Добавляем обработчик нажатия на карточку
            Surface(
                modifier = Modifier
                    .size(156.dp, 205.dp)
                    .padding(bottom = 10.dp)
                    .clickable { selectedSpecialist = it },
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 7.dp,
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 10.dp)
                        .padding(top = 120.dp)
                ) {

                    it.SpecName?.let { it1 ->
                        Text(
                            text = it1,
                            modifier = Modifier.padding(top = 6.dp),
                            color = Color.Black
                        )
                    }
                    it.Skills?.let { it1 ->
                        Text(
                            text = it1,
                            modifier = Modifier.padding(top = 6.dp),
                            color = Color.Black
                        )
                    }
                }
            }
        }

    }

    // Отображаем диалоговое окно, если выбран специалист
    if (selectedSpecialist != null) {
        SpecialistsInfo(
            specialists = listOf(selectedSpecialist!!),
            onDismiss = { selectedSpecialist = null }, // Сбрасываем выбранного специалиста при закрытии диалога
            onConfirm = { /* здесь можно добавить действия при подтверждении */ }
        )
    }
}



@OptIn
@Composable
fun SpecialistsInfo(
    specialists: List<Specialist>,
    viewModel: MainScreenViewModel = hiltViewModel(),
    onDismiss: () -> Unit, onConfirm: () -> Unit, onChange: (String) -> Unit = {},
) {
    var hasFocus by remember { mutableStateOf(false) }
    var shape = RoundedCornerShape(15.dp)
    Dialog(
        onDismissRequest = { onDismiss }, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = shape,
            modifier = Modifier.fillMaxWidth(0.95f)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                    }
                    ProfilePicture()
                    Divider()
                    LazyColumn {
                        items(specialists) { specialist ->
                            Text(
                                text = specialist.SpecName ?: "",
                                modifier = Modifier.padding(top = 6.dp),
                                color = Color.Black
                            )
                            Text(
                                text = specialist.Email ?: "",
                                modifier = Modifier.padding(top = 6.dp),
                                color = Color.Black
                            )
                            Text(
                                text = specialist.Experience ?: "",
                                modifier = Modifier.padding(top = 6.dp),
                                color = Color.Black
                            )
                            Text(
                                text = specialist.Rates ?: "",
                                modifier = Modifier.padding(top = 6.dp),
                                color = Color.Black
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { onConfirm() },
                            colors = ButtonDefaults.buttonColors(Color.Black)
                        ) {
                            Text(text = "Confirm")
                        }
                        Button(
                            onClick = { onDismiss() },
                            colors = ButtonDefaults.buttonColors(Color.Black)
                        ) {
                            Text(text = "Dismiss")
                        }
                    }
                }
            }
        }

    }
}
