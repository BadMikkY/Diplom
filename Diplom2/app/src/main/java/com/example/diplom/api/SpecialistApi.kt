package com.example.diplom.api

import com.example.diplom.model.Booking
import com.example.diplom.model.BookingResponse
import com.example.diplom.model.Review
import com.example.diplom.model.ReviewResponse
import com.example.diplom.model.Specialist
import com.example.diplom.model.SpecialistResponce
import com.example.diplom.model.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface SpecialistApi {
    @POST("spec/create")
    suspend fun registerSpec(
        @Body specialist: Specialist
    ): Response<SpecialistResponce>

    @POST("spec/login")
    suspend fun loginSpec(
        @Body specialist: Specialist
    ): Response<SpecialistResponce>

    @POST("spec/search")
    suspend fun searchSpecialists(
        @Body params: Map<String, String>
    ): List<Specialist>

    @GET("spec/all")
    suspend fun getAllSpecialists(): Response<List<Specialist>>

    data class ReviewRequest(val SpecialistID: Int)

    @POST("spec/getreviews")
    suspend fun getReviews(@Body request: ReviewRequest): List<Review>


    @POST("spec/reviews")
    suspend fun makeReview(
        @Body review: Review
    ): Response<ReviewResponse>

    @POST("booking/create")
    suspend fun createBooking(
        @Body booking: Booking
    ): Response<BookingResponse>

    @POST("spec/update")
    suspend fun updateSpec(
        @Body specialist: Specialist
    ): Response<SpecialistResponce>
}