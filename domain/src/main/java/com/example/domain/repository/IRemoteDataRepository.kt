package com.example.domain.repository

import com.example.domain.model.ApiResult

interface IRemoteDataRepository {
    suspend fun getPoints(pointsCount: Int): ApiResult
}