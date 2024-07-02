package com.example.diplom.screen.workerBookings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.components.ProfilePicture
import com.example.diplom.components.SearchTextField
import com.example.diplom.components.TextField
import com.example.diplom.model.Booking
import com.example.diplom.model.Specialist
import com.example.diplom.screen.bookingsScreen.BookingInfo
import com.example.diplom.screen.bookingsScreen.BookingsEvent
import com.example.diplom.screen.main.CardGrid
import com.example.diplom.screen.main.MainScreenEvent
import com.example.diplom.screen.main.MainScreenViewModel
import com.example.diplom.screen.main.SpecialistsInfo

@Composable
fun WorkerBookingsScreen(viewModel: WorkerBookingsViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(WorkerBookingsEvent.loadBookings)
    }
    val bookingsState = viewModel.bookingsListState



    Column {
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 22.dp)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = Color.Black
        )

        CardGridd(
            bookings = bookingsState,
        )
    }
}


@Composable
fun CardGridd(
    bookings: List<Booking>,
    viewModel: WorkerBookingsViewModel = hiltViewModel(),
) {
    val lazyListState = rememberLazyStaggeredGridState()
    var selectedBooking by remember { mutableStateOf<Booking?>(null) }
    var progress by remember { mutableStateOf<Booking?>(null) }

    LazyVerticalStaggeredGrid(
        state = lazyListState,
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 2.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 75.dp)
    ) {
        items(bookings) {

            Surface(
                modifier = Modifier
                    .size(156.dp, 205.dp)
                    .padding(bottom = 10.dp)
                    .clickable {
                        selectedBooking =
                            it;viewModel.handleEvent(WorkerBookingsEvent.getWorkProgress)
                    },
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
                    it.BookingDate?.let { it1 ->
                        Text(
                            text = "${it1}.06.2024",
                            modifier = Modifier.padding(top = 6.dp),
                            color = Color.Blue
                        )
                    }
                    it.Status?.let { it1 ->
                        Text(
                            text = it1,
                            modifier = Modifier.padding(top = 6.dp),
                            color = Color.Blue
                        )
                    }
                }
                if (selectedBooking != null) {
                    selectedBooking!!.SpecialistID?.let { it1 ->
                        viewModel.sharedPreferencesRepository.setSpecId(
                            it1
                        )
                    }

                    selectedBooking!!.BookingID?.let { it1 ->
                        viewModel.sharedPreferencesRepository.setBookingId(
                            it1
                        )
                    }


                    selectedBooking?.let { booking ->
                        WorkersBookingInfo(
                            bookings = listOf(booking),
                            onDismiss = {
                                selectedBooking = null
                            },
                            onConfirm = {
                                selectedBooking = null; viewModel.handleEvent(
                                WorkerBookingsEvent.confirmButtonClicked
                            );viewModel.handleEvent(WorkerBookingsEvent.loadBookings);viewModel.handleEvent(
                                WorkerBookingsEvent.createWorkProgress
                            )
                            },
                            onClose = {
                                selectedBooking = null; viewModel.handleEvent(
                                WorkerBookingsEvent.rejectButtonClicked
                            );viewModel.handleEvent(WorkerBookingsEvent.loadBookings)
                            },
                            onChangeProgress = {viewModel.handleEvent(WorkerBookingsEvent.updatePercentage)}
                        )
                    }
                }
            }
        }
    }
}


@OptIn
@Composable
fun WorkersBookingInfo(
    bookings: List<Booking>,
    viewModel: WorkerBookingsViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onChangeProgress:() ->Unit,
    onChange: (String) -> Unit = {},
    onClose: () -> Unit,
) {
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
                        items(bookings) { booking ->
                            Text(
                                text = "${booking.BookingDate}.06.2024" ?: "",
                                modifier = Modifier.padding(top = 6.dp),
                                color = Color.Black
                            )
                            Text(
                                text = booking.Status ?: "",
                                modifier = Modifier.padding(top = 6.dp),
                                color = Color.Black
                            )
                            if (booking.Status == "В процессе") {
                                Text(
                                    text = "Завершенность:${viewModel.workProgress?.Percentage ?: "..."}",
                                    modifier = Modifier.padding(top = 6.dp),
                                    color = Color.Black
                                )
                                TextField(
                                    viewModel.progress,
                                    R.string.progress,
                                    R.string.progress,
                                    16.dp,
                                    onChange = { progress -> viewModel.updateProgress(progress)  },
                                    supportText = R.string.nothing,
                                    isError = false
                                )
                                Text(
                                    text = "Комментарии: ${viewModel.workProgress?.Comment ?: "..."}", // Замените на реальные данные
                                    modifier = Modifier.padding(top = 6.dp),
                                    color = Color.Black
                                )
                                TextField(
                                    viewModel.comment,
                                    R.string. comment,
                                    R.string.comment,
                                    16.dp,
                                    onChange = { comment -> viewModel.updateComment(comment)  },
                                    supportText = R.string.nothing,
                                    isError = false
                                )
                                Button(
                                    onClick = { onChangeProgress() },
                                    colors = ButtonDefaults.buttonColors(Color.Black)
                                ) {
                                    Text(text = "Отправить")
                                }
                                Button(
                                    onClick = { onDismiss() },
                                    colors = ButtonDefaults.buttonColors(Color.Black)
                                ) {
                                    Text(text = "Закрыть")
                                }
                            } else {
                                // Если статус не "в процессе", отобразить кнопки "Подтвердить" и "Отказать"
                                Button(
                                    onClick = { onConfirm() },
                                    colors = ButtonDefaults.buttonColors(Color.Black)
                                ) {
                                    Text(text = "Подтвердить")
                                }
                                Button(
                                    onClick = { onClose() },
                                    colors = ButtonDefaults.buttonColors(Color.Red)
                                ) {
                                    Text(text = "Отказать")
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}