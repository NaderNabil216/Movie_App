package com.example.movies_listing.data.source.remote

import com.example.movies_listing.data.restful.MoviesListingApi
import com.example.movies_listing.domain.entities.query.MovieListingQuery
import com.example.movies_listing.domain.entities.response.MoviesListingRemoteResponse
import javax.inject.Inject

class MoviesListingRemoteSourceImpl @Inject constructor(private val moviesListingApi: MoviesListingApi) :
    MoviesListingRemoteSource {
    override suspend fun getMoviesList(movieListingQuery: MovieListingQuery): MoviesListingRemoteResponse {
        return moviesListingApi.getDiscoverMoviesListing(
            movieListingQuery.pageNumber,
            movieListingQuery.sortBy,
            movieListingQuery.language,
            movieListingQuery.api_key
        )
    }
}