package com.example.diplom.repository

import com.example.diplom.api.BookingApi
import com.example.diplom.model.Booking
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingsRepository @Inject constructor(
    private val api: BookingApi
) {
    suspend fun getBookingsForSpec(bookings: Booking): Response<List<Booking>> {
        return api.getBookingsForSpec(bookings)
    }

    suspend fun getBookingForUser(bookings:Booking): Response<List<Booking>>{
        return api.getBookingsForUser(bookings)
    }

    suspend fun deleteBooking(bookings:Booking): Response<List<Booking>>{
        return api.deledeBooking(bookings)
    }

    suspend fun updateStatus(bookings:Booking): Response<List<Booking>>{
        return api.updateStatus(bookings)
    }
}