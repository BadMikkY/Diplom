package com.example.diplom.model

data class Review(
    val ReviewID: Int? = null,
    val UserID: Int,
    val SpecialistID: Int,
    val Rating: String,
    val ReviewText: String
)