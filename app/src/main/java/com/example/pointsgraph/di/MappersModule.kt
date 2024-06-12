package com.example.pointsgraph.di

import com.example.data.mapper.PointsMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MappersModule {

    @Provides
    @Singleton
    fun providePointsMapper() = PointsMapper()
}
