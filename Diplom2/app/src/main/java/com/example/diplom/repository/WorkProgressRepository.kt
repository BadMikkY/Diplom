package com.example.diplom.repository

import com.example.diplom.api.WorkProgressApi
import com.example.diplom.model.WorkProgress
import com.example.diplom.model.WorkProgressResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkProgressRepository @Inject constructor(
    private val api: WorkProgressApi
) {
    suspend fun createWorkProgress(workProgress: WorkProgress) =
        api.createWorkProgress(workProgress)

    suspend fun getWorkProgress(bookingId: WorkProgress) = api.getProgress(bookingId)

    suspend fun updatePercentage(workProgressResponse: WorkProgressResponse) =
        api.updatePercentage(workProgressResponse)

    suspend fun addComment(workProgressResponse: WorkProgressResponse) =
        api.addComment(workProgressResponse)
}
