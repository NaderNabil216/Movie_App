package com.example.movies_listing.di

import com.example.movies_listing.data.repository.MoviesListingRepository
import com.example.movies_listing.data.repository.MoviesListingRepositoryImpl
import com.example.movies_listing.data.source.remote.MoviesListingRemoteSource
import com.example.movies_listing.data.source.remote.MoviesListingRemoteSourceImpl
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
        moviesListingRepositoryImpl: MoviesListingRepositoryImpl
    ): MoviesListingRepository

    @Binds
    abstract fun bindMoviesListingRemoteSource(
        moviesListingRemoteSourceImpl: MoviesListingRemoteSourceImpl
    ): MoviesListingRemoteSource

}