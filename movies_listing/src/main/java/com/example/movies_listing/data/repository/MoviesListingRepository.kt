package com.example.movies_listing.data.repository

import com.example.movies_listing.domain.entities.query.MovieListingQuery
import com.example.movies_listing.domain.entities.response.MoviesListingRemoteResponse

interface MoviesListingRepository {
    suspend fun getMoviesList(movieListingQuery: MovieListingQuery): MoviesListingRemoteResponse
}