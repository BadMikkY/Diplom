package com.example.diplom.screen.bookingsScreen

import com.example.diplom.screen.workerBookings.WorkerBookingsEvent

sealed class BookingsEvent {

    object loadBookings :
        BookingsEvent()

    object rejectButtonClicked :
        BookingsEvent()

    object confirmButtonClicked :
        BookingsEvent()
}