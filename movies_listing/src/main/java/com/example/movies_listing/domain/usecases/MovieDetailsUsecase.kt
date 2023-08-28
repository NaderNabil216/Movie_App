package com.example.movies_listing.domain.usecases

import com.example.movies_listing.BuildConfig
import com.example.movies_listing.data.repository.MoviesRepository
import com.example.movies_listing.domain.entities.local.MovieDetails
import com.example.movies_listing.domain.entities.query.MovieDetailsQuery
import com.example.movies_listing.domain.entities.remote.RemoteMovieDetails
import com.example.movies_listing.domain.mapper.MovieDetailsMapper
import com.youxel.core.data.mapper.CloudErrorMapper
import com.youxel.core.domain.usecase.base.RemoteUseCase
import javax.inject.Inject

class MovieDetailsUsecase  @Inject constructor(
    errorUtil: CloudErrorMapper,
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieDetailsMapper
) : RemoteUseCase<MovieDetailsQuery, RemoteMovieDetails, MovieDetails>(
    errorUtil
) {
    override suspend fun executeOnBackground(parameters: MovieDetailsQuery): RemoteMovieDetails {
        parameters.apiKey = BuildConfig.API_KEY
       return moviesRepository.getMovieDetails(parameters)
    }

    override suspend fun convert(dto: RemoteMovieDetails): MovieDetails {
        return mapper.convert(dto)
    }
}