package com.example.movies_listing.ui.fragment.details

sealed class MovieDetailsIntent {
    class GetMovieDetails(val movieId: Long) : MovieDetailsIntent()
}