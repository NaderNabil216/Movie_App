package com.example.movies_listing.ui.fragment.listing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.movies_listing.domain.entities.local.Movie
import com.example.movies_listing.domain.entities.query.MovieListingQuery
import com.example.movies_listing.domain.usecases.MoviesListingUsecase
import com.youxel.core.base.view_model.ApiState
import com.youxel.core.base.view_model.BasePagingViewModel
import com.youxel.core.domain.entities.base.ResponsePagingResultModel
import com.youxel.core.domain.usecase.base.CompletionBlock
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MoviesListingViewModel @Inject constructor(
    private val moviesListingUsecase: MoviesListingUsecase
) : BasePagingViewModel<Movie>() {

    private var moviesListingQuery =
        MovieListingQuery(pageNumber = pageNumber)

    private val moviesListingIntent = MutableLiveData<MoviesListingIntent>()

    private val moviesListingObserver = Observer<MoviesListingIntent> {
        when (it) {
            is MoviesListingIntent.GetMoviesList -> {
                resetPageNumberAndFetchPage()
            }
        }
    }

    internal fun send(intentType: MoviesListingIntent) {
        moviesListingIntent.value = intentType
    }

    override fun handelViewIntent() {
        super.handelViewIntent()
        viewModelScope.launch {
            moviesListingIntent.observeForever(moviesListingObserver)
        }
    }

    override fun fetchPage(completionBlock: CompletionBlock<ApiState<ResponsePagingResultModel<Movie>>>) {
        moviesListingUsecase.executeWithApiState(
            moviesListingQuery.also { it.pageNumber = pageNumber },
            completionBlock
        )
    }

    override fun reset() {
        super.reset()
        moviesListingIntent.removeObserver(moviesListingObserver)
    }
}