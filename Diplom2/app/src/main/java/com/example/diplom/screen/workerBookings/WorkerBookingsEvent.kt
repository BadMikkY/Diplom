package com.example.diplom.screen.workerBookings

import com.example.diplom.screen.main.MainScreenEvent

sealed class WorkerBookingsEvent {
    object loadBookings :
        WorkerBookingsEvent()

    object rejectButtonClicked :
        WorkerBookingsEvent()

    object confirmButtonClicked :
        WorkerBookingsEvent()

    object createWorkProgress :
        WorkerBookingsEvent()

    object getWorkProgress :
        WorkerBookingsEvent()

    object updatePercentage :
        WorkerBookingsEvent()



}