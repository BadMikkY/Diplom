package com.example.diplom.repository

import com.example.diplom.api.UserApi
import com.example.diplom.model.Booking
import com.example.diplom.model.User
import com.example.diplom.model.UserResponse
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(
    private val api: UserApi
) {
    suspend fun registerUser(user: User) = api.registerUser(user)

    suspend fun loginUser(user: User) = api.loginUser(user)
    suspend fun createBooking(booking: Booking) = api.createBooking(booking)

    suspend fun updateUser(userResponse: UserResponse) = api.updateUser(userResponse)
}