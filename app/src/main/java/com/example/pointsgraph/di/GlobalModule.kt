package com.example.pointsgraph.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class GlobalModule {
    @Singleton
    @Provides
    fun provideExceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            Log.wtf("Error ", " $throwable")
        }
    }
}
