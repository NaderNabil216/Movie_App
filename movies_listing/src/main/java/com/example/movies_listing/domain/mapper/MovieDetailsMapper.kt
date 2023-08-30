package com.example.movies_listing.domain.mapper

import com.example.movies_listing.domain.entities.local.MovieDetails
import com.example.movies_listing.domain.entities.remote.RemoteMovieDetails
import com.youxel.core.domain.usecase.base.ModelMapper
import com.youxel.core.domain.utils.changeDateFormat
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor(
    private val trailerMapper: TrailerMapper
) : ModelMapper<RemoteMovieDetails, MovieDetails> {
    override fun convert(from: RemoteMovieDetails?): MovieDetails {
        return from?.let {
            MovieDetails(
                id = it.id ?: 0,
                title = it.title.orEmpty(),
                releaseDate = it.releaseDate?.let { date ->
                    changeDateFormat(date, "yyyy-MM-dd", "yyyy")
                }.orEmpty(),
                posterImage = posterBaseUrl +(it.posterPath.orEmpty()),
                rating = getRating(it.voteAverage),
                overview = it.overview.orEmpty(),
                backdropImage = backdropBaseUrl + (it.backdropPath.orEmpty()),
                voteCount = getRatingCount(it.voteCount),
                isAdult = (it.adult ?: false).toString(),
                trailers = it.videos?.results?.filter { item ->
                    (item?.type == Trailer) && (item.site == YouTube)
                }?.map { item ->
                    trailerMapper.convert(item)
                } ?: listOf()


            )
        } ?: MovieDetails()
    }

    internal fun getRatingCount(ratingCount: Int?): String {
        return ratingCount?.let {
            when {
                ratingCount >= 1000 -> {
                    val value = ratingCount / 1000.0f
                    if (value == value.toInt().toFloat()) {
                        "${value.toInt()}k"
                    } else {
                        "${String.format("%.1f", value)}K"
                    }
                }
                else -> ratingCount.toString()
            }
        } ?: "0"
    }

    internal fun getRating(rating:Float?):String{
        return rating?.let {
            val roundedFloatValue = String.format("%.1f", it)
            roundedFloatValue.plus("/10")
        }?:"-"
    }

    companion object {
        private const val posterBaseUrl = "https://image.tmdb.org/t/p/w200"
        private const val backdropBaseUrl = "https://image.tmdb.org/t/p/w400"
        private const val Trailer = "Trailer"
        private const val YouTube = "YouTube"
    }
}