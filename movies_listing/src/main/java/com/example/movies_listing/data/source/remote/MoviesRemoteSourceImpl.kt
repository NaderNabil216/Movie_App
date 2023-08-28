package com.example.movies_listing.data.source.remote

import com.example.movies_listing.data.restful.MoviesApi
import com.example.movies_listing.domain.entities.query.MovieDetailsQuery
import com.example.movies_listing.domain.entities.query.MovieListingQuery
import com.example.movies_listing.domain.entities.remote.RemoteMovieDetails
import com.example.movies_listing.domain.entities.response.MoviesListingRemoteResponse
import javax.inject.Inject

class MoviesRemoteSourceImpl @Inject constructor(private val moviesListingApi: MoviesApi) :
    MoviesRemoteSource {
    override suspend fun getMoviesList(query: MovieListingQuery): MoviesListingRemoteResponse {
        return moviesListingApi.getDiscoverMoviesListing(
            query.pageNumber,
            query.sortBy,
            query.language,
            query.apiKey
        )
    }

    override suspend fun getMovieDetails(query: MovieDetailsQuery): RemoteMovieDetails {
        return moviesListingApi.getMovieDetails(
            query.movieId,
            query.language,
            query.appendToResponse,
            query.apiKey
        )
    }
}