package com.example.movies_listing.domain.entities.query

data class MovieDetailsQuery(
    var movieId: Long,
    val language: String = "en-US",
    val appendToResponse: String = "videos",
    var apiKey: String = ""
)