package com.example.data.repository

import com.example.data.api.ApiService
import com.example.data.mapper.PointsMapper
import com.example.domain.model.ApiResult
import com.example.domain.repository.IRemoteDataRepository
import javax.inject.Inject

class RemoteDataRepository @Inject constructor(
    private val apiService: ApiService,
    private val pointsMapper: PointsMapper,
) : IRemoteDataRepository {
    override suspend fun getPoints(pointsCount: Int): ApiResult {
        return try {
            val data = pointsMapper.mapPointsResponseToDomain(apiService.getPoints(count = pointsCount))
            ApiResult.Success(data)
        } catch (e: Exception) {
            ApiResult.Error(e.message)
        }
    }
}
