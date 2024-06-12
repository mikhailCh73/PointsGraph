package com.example.pointsgraph.di

import com.example.domain.useCase.getPointsUseCase.GetPointsUseCase
import com.example.domain.useCase.getPointsUseCase.IGetPointsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun provideGetPointsUseCase(useCase: GetPointsUseCase): IGetPointsUseCase

}
