package com.example.movies_listing.di

import com.example.movies_listing.data.restful.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    internal fun provideServicesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }
}