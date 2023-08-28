package com.example.movies_listing.data.source.remote

import com.example.movies_listing.domain.entities.query.MovieDetailsQuery
import com.example.movies_listing.domain.entities.query.MovieListingQuery
import com.example.movies_listing.domain.entities.remote.RemoteMovieDetails
import com.example.movies_listing.domain.entities.response.MoviesListingRemoteResponse

interface MoviesRemoteSource {
    suspend fun getMoviesList(movieListingQuery: MovieListingQuery): MoviesListingRemoteResponse
    suspend fun getMovieDetails(movieDetailsQuery: MovieDetailsQuery): RemoteMovieDetails
}