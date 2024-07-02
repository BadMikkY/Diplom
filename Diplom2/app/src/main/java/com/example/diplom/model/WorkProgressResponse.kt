package com.example.diplom.model

data class WorkProgressResponse(
    val ProgressID: Int,
    val BookingID: Int? = 0,
    val Comment: String? = "",
    val Percentage:Int? = 10
)