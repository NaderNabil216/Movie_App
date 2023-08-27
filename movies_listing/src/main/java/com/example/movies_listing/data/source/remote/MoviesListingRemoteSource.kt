package com.example.movies_listing.data.source.remote

import com.example.movies_listing.domain.entities.query.MovieListingQuery
import com.example.movies_listing.domain.entities.response.MoviesListingRemoteResponse

interface MoviesListingRemoteSource {
    suspend fun getMoviesList(movieListingQuery: MovieListingQuery): MoviesListingRemoteResponse
}