package com.example.movies_listing.domain.entities.query

data class MovieListingQuery(
    var pageNumber: Int,
    val sortBy: String = "popularity.desc",
    val language: String = "en-US",
    var apiKey: String=""
)
