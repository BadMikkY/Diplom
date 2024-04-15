package com.example.diplom.api

import com.example.diplom.model.User
import com.example.diplom.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi{
    @POST("user/create")
    suspend fun registerUser(
        @Body user: User
    ):Response<UserResponse>

    @POST("user/login")
    suspend fun loginUser(
        @Body user: User
    ):Response<UserResponse>
}