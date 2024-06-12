package com.example.pointsgraph.di

import com.example.data.repository.RemoteDataRepository
import com.example.domain.repository.IRemoteDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideRemoteDataRepo(repo: RemoteDataRepository): IRemoteDataRepository

}
