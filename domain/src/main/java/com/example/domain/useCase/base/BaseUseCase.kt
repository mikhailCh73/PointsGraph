package com.example.domain.useCase.base

interface BaseUseCase<P : Any?, T> {
    suspend fun invoke(param: P): T
}
