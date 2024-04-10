package com.example.diplom.api

import com.example.diplom.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi{
    @POST("/create")
    suspend fun registerUser(
        @Body user: User
    ):Response<User>
}