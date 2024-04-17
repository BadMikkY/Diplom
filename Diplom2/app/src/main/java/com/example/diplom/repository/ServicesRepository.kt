package com.example.diplom.repository

import com.example.diplom.api.ServicesApi
import com.example.diplom.api.SpecialistApi
import com.example.diplom.model.Service
import com.example.diplom.model.ServiceResponse
import com.example.diplom.model.Specialist
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServicesRepository @Inject constructor(
    private val api: ServicesApi
) {
    suspend fun getAllServices(): Response<List<Service>> {
        return api.getAllServices()
    }

    suspend fun searchServices(name: String, description: String): List<Service>? {
        val params = mapOf("serviceName" to name, "description" to description)
        return api.searchServices(params)
    }

    suspend fun createService(service: Service) = api.createService(service)
}