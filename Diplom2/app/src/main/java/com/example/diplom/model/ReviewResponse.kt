package com.example.diplom.model

data class ReviewResponse(
    val ReviewId: Int,
    val UserId: Int,
    val SpecialistId: Int,
    val Rating: String,
    val ReviewText: String
)