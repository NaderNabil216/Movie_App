package com.example.movies_listing.domain.usecases

import com.example.movies_listing.data.repository.MoviesListingRepository
import com.example.movies_listing.domain.entities.local.Movie
import com.example.movies_listing.domain.entities.query.MovieListingQuery
import com.example.movies_listing.domain.entities.response.MoviesListingRemoteResponse
import com.example.movies_listing.domain.mapper.MovieMapper
import com.youxel.core.data.mapper.CloudErrorMapper
import com.youxel.core.domain.entities.base.ResponsePagingResultModel
import com.youxel.core.domain.usecase.base.RemoteUseCase
import javax.inject.Inject

class MoviesListingUsecase @Inject constructor(
    errorUtil: CloudErrorMapper,
    private val moviesListingRepository: MoviesListingRepository,
    private val mapper: MovieMapper
) : RemoteUseCase<MovieListingQuery, MoviesListingRemoteResponse, ResponsePagingResultModel<Movie>>(
    errorUtil
) {
    override suspend fun executeOnBackground(parameters: MovieListingQuery): MoviesListingRemoteResponse {
        parameters.api_key = "c9856d0cb57c3f14bf75bdc6c063b8f3"
        return moviesListingRepository.getMoviesList(parameters)
    }

    override suspend fun convert(dto: MoviesListingRemoteResponse): ResponsePagingResultModel<Movie> {
        val mappedList = dto.results?.map {
            mapper.convert(it)
        } ?: listOf()
        return ResponsePagingResultModel(
            mappedList,
            dto.totalResults ?: 0,
            dto.totalPages ?: 0
        )
    }

}