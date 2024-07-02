package com.example.diplom.screen.bookingsScreen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.components.ProfilePicture
import com.example.diplom.model.Booking
import com.example.diplom.model.Service
import com.example.diplom.screen.main.MainScreenViewModel
import com.example.diplom.screen.workerBookings.CardGridd
import com.example.diplom.screen.workerBookings.WorkerBookingsEvent
import com.example.diplom.screen.workerBookings.WorkerBookingsViewModel
import com.example.diplom.screen.workerMain.ServiceInfo
import com.example.diplom.screen.workerMain.WorkerMainEvent


@Composable
fun BookingsScreen(viewModel: BookingsScreenViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(BookingsEvent.loadBookings)
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

        CardGriddd(
            bookings = bookingsState,
        )
    }
}

@Composable
fun CardGriddd(
    bookings: List<Booking>,
    viewModel: BookingsScreenViewModel = hiltViewModel(),
) {
    var selectedBooking by remember { mutableStateOf<Booking?>(null) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 75.dp)
    ) {
        items(bookings.chunked(2)) { rowBookings ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (booking in rowBookings) {
                    Surface(
                        modifier = Modifier
                            .size(156.dp, 205.dp)
                            .padding(bottom = 10.dp)
                            .clickable { selectedBooking = booking },
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
                            booking.BookingDate?.let { it1 ->
                                Text(
                                    text = "${it1}.06.2024",
                                    modifier = Modifier.padding(top = 6.dp),
                                    color = Color.Black
                                )
                            }
                            booking.Status?.let { it1 ->
                                Text(
                                    text = it1,
                                    modifier = Modifier.padding(top = 6.dp),
                                    color = Color.Black
                                )
                            }
                        }
                        if (selectedBooking != null) {
                            selectedBooking!!.UserID?.let { it1 ->
                                viewModel.sharedPreferencesRepository.setUserId(
                                    it1
                                )
                            }
                            selectedBooking!!.BookingID?.let { it1 ->
                                viewModel.sharedPreferencesRepository.setBookingId(
                                    it1
                                )
                            }

                            selectedBooking?.let { booking ->
                                BookingInfo(
                                    bookings = listOf(booking),
                                    onDismiss = {
                                        selectedBooking = null; viewModel.handleEvent(
                                        BookingsEvent.rejectButtonClicked
                                    );viewModel.handleEvent(BookingsEvent.loadBookings)
                                    },
                                    onConfirm = {
                                        selectedBooking = null; viewModel.handleEvent(
                                        BookingsEvent.confirmButtonClicked
                                    );viewModel.handleEvent(BookingsEvent.loadBookings)
                                    },
                                    onClose = { selectedBooking = null },
                                    onDeleteBooking = {
                                        selectedBooking =
                                            null; viewModel.handleEvent(BookingsEvent.confirmButtonClicked);viewModel.handleEvent(
                                        BookingsEvent.rejectButtonClicked
                                    );viewModel.handleEvent(BookingsEvent.loadBookings)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn
@Composable
fun BookingInfo(
    bookings: List<Booking>,
    viewModel: WorkerBookingsViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onChange: (String) -> Unit = {},
    onDeleteBooking: () -> Unit = {},
    onClose: () -> Unit,
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
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .width(200.dp) // Установите ширину, которую вы хотите
                .height(300.dp) // Установите высоту, которую вы хотите
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
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

                                // Если статус "в процессе", отобразить дополнительные строки и кнопку "Закрыть"
                                if (booking.Status == "В процессе") {
                                    viewModel.handleEvent(WorkerBookingsEvent.getWorkProgress)
                                    Text(
                                        text = "Завершенность:${viewModel.workProgress?.Percentage}",
                                        modifier = Modifier.padding(top = 6.dp),
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "Комментарии: ${viewModel.workProgress?.Comment ?: "..."}",
                                        modifier = Modifier.padding(top = 6.dp),
                                        color = Color.Black
                                    )
                                    Button(
                                        onClick = { onClose() },
                                        colors = ButtonDefaults.buttonColors(Color(173, 216, 230)),
                                    ) {
                                        Text(text = "Закрыть")
                                    }
                                    if (viewModel.workProgress?.Percentage == 100) {
                                        Button(
                                            onClick = { onDeleteBooking() },
                                            colors = ButtonDefaults.buttonColors(
                                                Color(
                                                    173,
                                                    216,
                                                    230
                                                )
                                            ),
                                        ) {
                                            Text(text = "Завершить заказ") 
                                        }
                                    }
                                } else {
                                    // Если статус не "в процессе", отобразить кнопки "Подтвердить" и "Отказать"
                                    Button(
                                        onClick = { onConfirm() },
                                        colors = ButtonDefaults.buttonColors(Color.Red),
                                    ) {
                                        Text(text = "Подтвердить")
                                    }
                                    Button(
                                        onClick = { onDeleteBooking() },
                                        colors = ButtonDefaults.buttonColors(Color(173, 216, 230)),
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
}
