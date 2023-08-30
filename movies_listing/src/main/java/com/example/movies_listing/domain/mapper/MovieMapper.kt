package com.example.movies_listing.domain.mapper

import com.example.movies_listing.domain.entities.local.Movie
import com.example.movies_listing.domain.entities.remote.RemoteMovie
import com.youxel.core.domain.usecase.base.ModelMapper
import com.youxel.core.domain.utils.changeDateFormat
import javax.inject.Inject

class MovieMapper @Inject constructor():ModelMapper<RemoteMovie,Movie> {
    override fun convert(from: RemoteMovie?): Movie {
        return from?.let {
            Movie(
                id = it.id?:0,
                title = it.title.orEmpty(),
                releaseDate = it.releaseDate?.let {date->
                    changeDateFormat(date, "yyyy-MM-dd", "yyyy")
                }.orEmpty(),
                posterImage = posterBaseUrl+(it.posterPath.orEmpty()),
                rating = (it.voteAverage?.div(2.0f))?:0.0f,
                overview = it.overview.orEmpty()
            )
        }?:Movie()
    }
    companion object {
        private const val posterBaseUrl="https://image.tmdb.org/t/p/w200"
    }
}
