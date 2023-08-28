package com.example.movies_listing.ui.fragment.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.movies_listing.domain.entities.local.MovieDetails
import com.example.movies_listing.domain.entities.query.MovieDetailsQuery
import com.example.movies_listing.domain.usecases.MovieDetailsUsecase
import com.youxel.core.base.fragment.BaseUiHelper
import com.youxel.core.base.view_model.ApiState
import com.youxel.core.base.view_model.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieDetailsViewModel  @Inject constructor(
    private val movieDetailsUsecase: MovieDetailsUsecase
):BaseViewModel() {
    private val movieDetailsIntent = MutableLiveData<MovieDetailsIntent>()
    private val movieDetailsIntentObserver = Observer<MovieDetailsIntent> {
        when (it) {
            is MovieDetailsIntent.GetMovieDetails -> {

                getMovieDetails(MovieDetailsQuery(it.movieId))
            }
           
        }
    }

    private val _movieDetailsMutableStateFlow =
        MutableStateFlow<ApiState<MovieDetails>>(ApiState.Idle)
    val movieDetailsMutableStateFlow: StateFlow<ApiState<MovieDetails>> =
        _movieDetailsMutableStateFlow

    private fun getMovieDetails(movieDetailsQuery: MovieDetailsQuery) {
        callApiWithApiState(_movieDetailsMutableStateFlow) {
            movieDetailsUsecase.executeWithApiState(
                movieDetailsQuery,
                it
            )
        }
    }

    fun send(intent: MovieDetailsIntent) {
        movieDetailsIntent.value = intent
    }

    override fun reset() {
        super.reset()
        movieDetailsIntent.removeObserver(movieDetailsIntentObserver)
        _movieDetailsMutableStateFlow.value = ApiState.Idle
    }

    override fun handelViewIntent() {
        super.handelViewIntent()
        movieDetailsIntent.observeForever(movieDetailsIntentObserver)
    }
}