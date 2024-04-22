package com.example.diplom.repository

import com.example.diplom.api.SpecialistApi
import com.example.diplom.api.UserApi
import com.example.diplom.model.Service
import com.example.diplom.model.Specialist
import com.example.diplom.model.User
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpecialistRepository @Inject constructor(
    private val api: SpecialistApi
) {
    suspend fun registerSpec(specialist: Specialist) = api.registerSpec(specialist)

    suspend fun loginSpec(specialist: Specialist) = api.loginSpec(specialist)

    suspend fun getAllSpecialists(): Response<List<Specialist>> {
        return api.getAllSpecialists()
    }

    suspend fun searchSpecialists(name: String, skills: String): List<Specialist>? {
        val params = mapOf("name" to name, "skills" to skills)
        return api.searchSpecialists(params)
    }
}