package com.example.diplom.model

data class User(
    val UserName: String? = null,
    val Password: String?,
    val Email: String = "",
    var Role: String = "user"
)