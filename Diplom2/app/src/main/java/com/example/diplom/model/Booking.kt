package com.example.diplom.model

data class Booking(
    val bookingId: Int,
    val userId: Int,
    val specialistId: Int,
    val bookingDate: String,
    val Status: String,
    val reviewId: Int
)