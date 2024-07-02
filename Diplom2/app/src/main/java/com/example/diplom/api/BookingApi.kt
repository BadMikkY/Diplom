package com.example.diplom.api

import com.example.diplom.model.Booking
import com.example.diplom.model.Review
import com.example.diplom.model.ReviewResponse
import com.example.diplom.model.Specialist
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BookingApi {

    @POST("booking/getBySpecID")
    suspend fun getBookingsForSpec(@Body specID: Booking)
            : Response<List<Booking>>


    @POST("booking/getByUserID")
    suspend fun getBookingsForUser(@Body userID: Booking): Response<List<Booking>>

    @POST("booking/remove")
    suspend fun deledeBooking(@Body bookingID: Booking): Response<List<Booking>>

    @POST("booking/updateStatus")
    suspend fun updateStatus(@Body bookings: Booking): Response<List<Booking>>

}