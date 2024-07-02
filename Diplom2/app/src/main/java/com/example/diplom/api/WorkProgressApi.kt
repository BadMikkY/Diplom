package com.example.diplom.api

import android.health.connect.datatypes.units.Percentage
import com.example.diplom.model.Booking
import com.example.diplom.model.WorkProgress
import com.example.diplom.model.WorkProgressResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WorkProgressApi {

    @POST("progress/create")
    suspend fun createWorkProgress(@Body workProgress: WorkProgress): Response<WorkProgressResponse>


    @POST("progress/addcomment")//dopisatt
    suspend fun addComment(
        @Body workProgressResponse: WorkProgressResponse
    ): Response<WorkProgressResponse>?
    @POST("progress/getprogress")
    suspend fun getProgress(@Body bookingID: WorkProgress): Response<WorkProgressResponse>

    @POST("progress/updatepercentage")
    suspend fun updatePercentage(
        @Body workProgressResponse: WorkProgressResponse
    ): Response<WorkProgressResponse>?


}