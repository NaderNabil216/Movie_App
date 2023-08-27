package com.example.movies_listing.data.restful

import com.example.movies_listing.data.utils.Config
import com.example.movies_listing.domain.entities.response.MoviesListingRemoteResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesListingApi {

    @GET(Config.DISCOVER_MOVIES_LISTING)
    suspend fun getDiscoverMoviesListing(
        @Query("page") page:Int,
        @Query("sort_by") sortBy:String,
        @Query("language") language:String,
        @Query("api_key") apiKey:String
    ): MoviesListingRemoteResponse
}