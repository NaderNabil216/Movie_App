package com.example.movies_listing.ui.fragment.listing

sealed class MoviesListingIntent {
    object GetMoviesList : MoviesListingIntent()
}