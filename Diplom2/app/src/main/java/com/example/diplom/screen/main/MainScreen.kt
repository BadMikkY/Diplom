package com.example.diplom.screen.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.components.ProfilePicture
import com.example.diplom.components.SearchTextField
import com.example.diplom.components.TextField
import com.example.diplom.model.Review
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
    var selectedSpecialist by remember { mutableStateOf<Specialist?>(null) }

    LazyVerticalStaggeredGrid(
        state = lazyListState,
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 2.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 75.dp)
    ) {
        items(specialists) {
            if (specialists.indexOf(it) == specialists.size - 1) {
                onEndReached()
            }
            Surface(
                modifier = Modifier
                    .size(156.dp, 205.dp)
                    .padding(bottom = 10.dp)
                    .clickable { selectedSpecialist = it },
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 7.dp,
                color = Color(135, 206, 235)
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
                            color = Color(0, 0, 139) // темно-синий цвет текста
                        )
                    }
                    it.Skills?.let { it1 ->
                        Text(
                            text = it1,
                            modifier = Modifier.padding(top = 6.dp),
                            color = Color(0, 0, 139) // темно-синий цвет текста
                        )
                    }
                }
                if (selectedSpecialist != null) {
                    selectedSpecialist!!.SpecialistID?.let { it1 ->
                        viewModel.sharedPreferencesRepository.setSpecId(
                            it1
                        )
                    }
                    selectedSpecialist!!.SpecialistID?.let { it1 -> viewModel.getReviews(it1) }
                    val reviewsList by viewModel.reviewsListState.collectAsState(initial = emptyList())
                    SpecialistsInfo(
                        specialists = listOf(selectedSpecialist!!),
                        onDismiss = { selectedSpecialist = null },
                        onConfirm = { viewModel.handleEvent(MainScreenEvent.confirmButtonClicked);viewModel.getSpecIDD(); },
                        onHire = { viewModel.handleEvent(MainScreenEvent.hireButtonClicked) },
                        reviews = reviewsList
                    )
                }
            }
        }
    }
}



@OptIn
@Composable
fun SpecialistsInfo(
    specialists: List<Specialist>,
    reviews: List<Review>,
    viewModel: MainScreenViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onHire: () -> Unit,
    onChange: (String) -> Unit = {},
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
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.cardbackground),
                    contentDescription = "im1",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f),
                    contentScale = ContentScale.FillBounds
                )
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
                        ProfilePicture()
                        Button(
                            onClick = { onHire() },
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text(text = "Нанять")
                        }
                        Divider()
                        LazyColumn {
                            items(specialists) { specialist ->
                                Text(
                                    text = "Имя:${specialist.SpecName}" ?: "",
                                    modifier = Modifier.padding(top = 6.dp),
                                    color = Color.White
                                )
                                Text(
                                    text = "Email:${specialist.Email}" ?: "",
                                    modifier = Modifier.padding(top = 6.dp),
                                    color = Color.White
                                )
                                Text(
                                    text = "Опыт работы:${specialist.Experience}" ?: "",
                                    modifier = Modifier.padding(top = 6.dp),
                                    color = Color.White
                                )
                                Text(
                                    text = "Рейтинг:${specialist.Rates}" ?: "",
                                    modifier = Modifier.padding(top = 6.dp),
                                    color = Color.White
                                )
                            }
                        }
                        LazyColumn {
                            items(reviews) { review ->
                                Text(
                                    text = "Отзыв: ${review.ReviewText}",
                                    modifier = Modifier.padding(top = 6.dp),
                                    color = Color.White
                                )
                                Text(
                                    text = "Рейтинг: ${review.Rating}",
                                    modifier = Modifier.padding(top = 6.dp),
                                    color = Color.White
                                )
                            }
                        }
                        Text(text = "Оставить свой отзыв:")
                        TextField(
                            viewModel.review,
                            R.string.review,
                            R.string.review,
                            16.dp,
                            onChange = { review -> viewModel.updateReview(review) },
                            supportText = R.string.nothing,
                            isError = false
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { onConfirm() },
                                colors = ButtonDefaults.buttonColors(Color(100, 216, 230)),
                            ) {
                                Text(text = "Отправить", color = Color.White)
                            }
                            Button(
                                onClick = { onDismiss() },
                                colors = ButtonDefaults.buttonColors(Color(100, 216, 230)),

                                ) {
                                Text(text = "Закрыть")
                            }
                        }
                    }
                }
            }
        }
    }
}
