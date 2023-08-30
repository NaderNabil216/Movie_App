package com.example.movies_listing.ui.fragment.listing

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movies_listing.R
import com.example.movies_listing.databinding.FragmentMoviesListingBinding
import com.example.movies_listing.databinding.ItemMovieBinding
import com.example.movies_listing.domain.entities.local.Movie
import com.youxel.core.base.adapter.diffutilsAdapter.BaseRecyclerAdapter
import com.youxel.core.base.fragment.BasePagingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MoviesListingFragment :
    BasePagingFragment<Movie, ItemMovieBinding, FragmentMoviesListingBinding, MoviesListingViewModel, MoviesListingUiHelper>(
        FragmentMoviesListingBinding::inflate
    ) {
    override val pagingAdapter: BaseRecyclerAdapter<ItemMovieBinding, Movie> =
        MoviesListingAdapter(::onMovieItemClickListener)
    override val emptyViewTxtRes: Int = com.youxel.core.R.string.empty_list
    override val viewModel: MoviesListingViewModel by viewModels()

    @Inject
    override lateinit var fragmentHelper: MoviesListingUiHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarListener?.run {
            showOnlyHeaderToolbar()
            setActivityToolbarTitle(
                getString(R.string.discover_movies),
                Gravity.CENTER
            )

        }
    }

    private fun onMovieItemClickListener(movieId: Long) {
        findNavController().navigate(
            MoviesListingFragmentDirections.actionMoviesListingFragmentToMovieDetailsFragment(
                movieId
            )
        )
    }

    override fun onInternetStatusChanged(isConnected: Boolean) {
        if (isConnected) {
            viewModel.send(MoviesListingIntent.GetMoviesList)
        }
        fragmentHelper.handleNoInternetConnectionView(binding, isConnected)
    }
}