package com.example.data.api

import com.example.data.model.PointsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/test/points/")
    suspend fun getPoints(
        @Query("count") count: Int,
    ): PointsResponse
}
