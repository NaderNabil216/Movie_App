package com.example.movies_listing.domain.entities.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RemoteMovieDetails(

	@Expose @field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@Expose @field:SerializedName("imdb_id")
	val imdbId: String? = null,

	@Expose @field:SerializedName("videos")
	val videos: Videos? = null,

	@Expose @field:SerializedName("video")
	val video: Boolean? = null,

	@Expose @field:SerializedName("title")
	val title: String? = null,

	@Expose @field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@Expose @field:SerializedName("revenue")
	val revenue: Int? = null,

	@Expose @field:SerializedName("genres")
	val genres: List<GenresItem?>? = null,

	@Expose @field:SerializedName("popularity")
	val popularity: Any? = null,

	@Expose @field:SerializedName("production_countries")
	val productionCountries: List<ProductionCountriesItem?>? = null,

	@Expose @field:SerializedName("id")
	val id: Long? = null,

	@Expose @field:SerializedName("vote_count")
	val voteCount: Int? = null,

	@Expose @field:SerializedName("budget")
	val budget: Int? = null,

	@Expose @field:SerializedName("overview")
	val overview: String? = null,

	@Expose @field:SerializedName("original_title")
	val originalTitle: String? = null,

	@Expose @field:SerializedName("runtime")
	val runtime: Int? = null,

	@Expose @field:SerializedName("poster_path")
	val posterPath: String? = null,

	@Expose @field:SerializedName("spoken_languages")
	val spokenLanguages: List<SpokenLanguagesItem?>? = null,

	@Expose @field:SerializedName("production_companies")
	val productionCompanies: List<ProductionCompaniesItem?>? = null,

	@Expose @field:SerializedName("release_date")
	val releaseDate: String? = null,

	@Expose @field:SerializedName("vote_average")
	val voteAverage: Float? = null,

	@Expose @field:SerializedName("belongs_to_collection")
	val belongsToCollection: Any? = null,

	@Expose @field:SerializedName("tagline")
	val tagline: String? = null,

	@Expose @field:SerializedName("adult")
	val adult: Boolean? = null,

	@Expose @field:SerializedName("homepage")
	val homepage: String? = null,

	@Expose @field:SerializedName("status")
	val status: String? = null
)

data class GenresItem(

	@Expose @field:SerializedName("name")
	val name: String? = null,

	@Expose @field:SerializedName("id")
	val id: Int? = null
)

data class RemoteVideoData(

	@Expose @field:SerializedName("site")
	val site: String? = null,

	@Expose @field:SerializedName("size")
	val size: Int? = null,

	@Expose @field:SerializedName("iso_3166_1")
	val iso31661: String? = null,

	@Expose @field:SerializedName("name")
	val name: String? = null,

	@Expose @field:SerializedName("official")
	val official: Boolean? = null,

	@Expose @field:SerializedName("id")
	val id: String? = null,

	@Expose @field:SerializedName("type")
	val type: String? = null,

	@Expose @field:SerializedName("published_at")
	val publishedAt: String? = null,

	@Expose @field:SerializedName("iso_639_1")
	val iso6391: String? = null,

	@Expose @field:SerializedName("key")
	val key: String? = null
)

data class ProductionCompaniesItem(

	@Expose @field:SerializedName("logo_path")
	val logoPath: String? = null,

	@Expose @field:SerializedName("name")
	val name: String? = null,

	@Expose @field:SerializedName("id")
	val id: Int? = null,

	@Expose @field:SerializedName("origin_country")
	val originCountry: String? = null
)

data class Videos(

	@Expose @field:SerializedName("results")
	val results: List<RemoteVideoData?>? = null
)

data class SpokenLanguagesItem(

	@Expose @field:SerializedName("name")
	val name: String? = null,

	@Expose @field:SerializedName("iso_639_1")
	val iso6391: String? = null,

	@Expose @field:SerializedName("english_name")
	val englishName: String? = null
)

data class ProductionCountriesItem(

	@Expose @field:SerializedName("iso_3166_1")
	val iso31661: String? = null,

	@Expose @field:SerializedName("name")
	val name: String? = null
)
