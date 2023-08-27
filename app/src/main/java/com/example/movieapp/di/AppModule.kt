package com.example.movieapp.di

import com.youxel.core.data.local.StorageManager
import com.youxel.core.data.local.StorageManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindStorageManager(storageManagerImpl: StorageManagerImpl): StorageManager
}