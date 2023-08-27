package com.example.movies_listing.domain.entities.local

data class Movie(
    val id:Int=0,
    val title:String="",
    val posterImage:String="",
    val releaseDate:String="",
    val rating:Float=0.0f,
    val overview:String=""
)
