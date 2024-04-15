package com.example.diplom.model

data class Specialist(
    val Email: String,
    val Skills: String? = null,
    val SpecName: String? = null,
    val Password: String,
    val Experience: String? = null,
    val Schedule: String? = null,
    val Rates: String? = null
)