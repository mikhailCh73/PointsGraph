package com.example.domain.useCase.getPointsUseCase

import com.example.domain.model.ApiResult
import com.example.domain.repository.IRemoteDataRepository
import javax.inject.Inject


class GetPointsUseCase @Inject constructor(private val remoteDataRepository: IRemoteDataRepository) :
    IGetPointsUseCase {
    override suspend fun invoke(param: Int): ApiResult {
        return remoteDataRepository.getPoints(param)
    }
}
