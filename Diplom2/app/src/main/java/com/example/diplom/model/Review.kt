package com.example.diplom.model

data class Review(
    val reviewId: Int,
    val userId: Int,
    val specialistId: Int,
    val rating: String,
    val reviewText: String
)