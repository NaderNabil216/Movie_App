package com.example.movies_listing.domain.entities.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RemoteMovie(

	@Expose
	@field:SerializedName("overview")
	val overview: String? = null,

	@Expose
	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@Expose
	@field:SerializedName("original_title")
	val originalTitle: String? = null,

	@Expose
	@field:SerializedName("video")
	val video: Boolean? = null,

	@Expose
	@field:SerializedName("title")
	val title: String? = null,

	@Expose
	@field:SerializedName("genre_ids")
	val genreIds: List<Int?>? = null,

	@Expose
	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@Expose
	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@Expose
	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@Expose
	@field:SerializedName("popularity")
	val popularity: Any? = null,

	@Expose
	@field:SerializedName("vote_average")
	val voteAverage: Float? = null,

	@Expose
	@field:SerializedName("id")
	val id: Int? = null,

	@Expose
	@field:SerializedName("adult")
	val adult: Boolean? = null,

	@Expose
	@field:SerializedName("vote_count")
	val voteCount: Int? = null
)
