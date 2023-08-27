package com.example.movies_listing.data.repository

import com.example.movies_listing.data.source.remote.MoviesListingRemoteSource
import com.example.movies_listing.domain.entities.query.MovieListingQuery
import com.example.movies_listing.domain.entities.response.MoviesListingRemoteResponse
import javax.inject.Inject

class MoviesListingRepositoryImpl @Inject constructor(private val moviesListingRemoteSource: MoviesListingRemoteSource) :
    MoviesListingRepository {
    override suspend fun getMoviesList(movieListingQuery: MovieListingQuery): MoviesListingRemoteResponse {
        return moviesListingRemoteSource.getMoviesList(movieListingQuery)
    }
}