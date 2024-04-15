package com.example.diplom.api

import com.example.diplom.model.Specialist
import com.example.diplom.model.SpecialistResponce
import com.example.diplom.model.User
import com.example.diplom.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SpecialistApi {
    @POST("spec/create")
    suspend fun registerSpec(
        @Body specialist: Specialist
    ): Response<SpecialistResponce>

    @POST("spec/login")
    suspend fun loginSpec(
        @Body specialist: Specialist
    ):Response<SpecialistResponce>


}