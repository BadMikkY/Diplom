package com.example.diplom.repository

import com.example.diplom.navigation.UserApi
import com.example.diplom.model.User
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


@ViewModelScoped
class UserRepository @Inject constructor(
    private val api: UserApi
) {
    suspend fun registerUser(user: User) = api.registerUser(user)
}