package com.example.movies_listing.di

import com.example.movies_listing.data.repository.MoviesRepository
import com.example.movies_listing.data.repository.MoviesRepositoryImpl
import com.example.movies_listing.data.source.remote.MoviesRemoteSource
import com.example.movies_listing.data.source.remote.MoviesRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesListingModule {

    @Binds
    abstract fun bindMoviesListingRepository(
        moviesListingRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

    @Binds
    abstract fun bindMoviesListingRemoteSource(
        moviesListingRemoteSourceImpl: MoviesRemoteSourceImpl
    ): MoviesRemoteSource

}