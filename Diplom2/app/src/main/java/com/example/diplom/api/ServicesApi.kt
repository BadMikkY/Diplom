package com.example.diplom.api

import com.example.diplom.model.Service
import com.example.diplom.model.ServiceResponse
import com.example.diplom.model.Specialist
import com.example.diplom.model.SpecialistResponce
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServicesApi {

    @GET("service/all")
    suspend fun getAllServices(): Response<List<Service>>

    @POST("service/search")
    suspend fun searchServices(
        @Body params: Map<String, String>
    ): List<Service>

    @POST("service/create")
    suspend fun createService(@Body service: Service): Response<ServiceResponse>

}