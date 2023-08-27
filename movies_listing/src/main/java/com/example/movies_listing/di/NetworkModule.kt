package com.example.movies_listing.di

import com.example.movies_listing.data.restful.MoviesListingApi
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
    internal fun provideServicesApi(retrofit: Retrofit): MoviesListingApi {
        return retrofit.create(MoviesListingApi::class.java)
    }
}