package com.example.movies_listing.data.repository

import com.example.movies_listing.data.source.remote.MoviesRemoteSource
import com.example.movies_listing.domain.entities.query.MovieDetailsQuery
import com.example.movies_listing.domain.entities.query.MovieListingQuery
import com.example.movies_listing.domain.entities.remote.RemoteMovieDetails
import com.example.movies_listing.domain.entities.response.MoviesListingRemoteResponse
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val moviesListingRemoteSource: MoviesRemoteSource) :
    MoviesRepository {
    override suspend fun getMoviesList(movieListingQuery: MovieListingQuery): MoviesListingRemoteResponse {
        return moviesListingRemoteSource.getMoviesList(movieListingQuery)
    }

    override suspend fun getMovieDetails(movieDetailsQuery: MovieDetailsQuery): RemoteMovieDetails {
        return moviesListingRemoteSource.getMovieDetails(movieDetailsQuery)
    }
}