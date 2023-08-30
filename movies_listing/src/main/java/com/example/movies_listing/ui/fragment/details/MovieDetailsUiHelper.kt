package com.example.movies_listing.ui.fragment.details

import android.content.Context
import com.example.movies_listing.R
import com.example.movies_listing.databinding.FragmentMovieDetailsBinding
import com.example.movies_listing.domain.entities.local.MovieDetails
import com.youxel.core.base.dialog.BaseNetworkingDialog
import com.youxel.core.base.fragment.BaseUiHelper
import com.youxel.core.domain.entities.base.ErrorStatus
import com.youxel.core.utils.loadImg
import javax.inject.Inject

class MovieDetailsUiHelper @Inject constructor(private val baseNetworkingDialog: BaseNetworkingDialog) :
    BaseUiHelper() {
    fun setData(
        binding: FragmentMovieDetailsBinding,
        trailerAdapter: MovieDetailsTrailerAdapter,
        movieDetails: MovieDetails
    ) {
        binding.run {
            movieDetails.run {
                ivPoster.loadImg(posterImage)
                ivBackdrop.loadImg(backdropImage)
                tvMovieTitle.text = title
                tvReleaseDate.text = releaseDate
                tvVoteAverage.text = binding.root.context.getString(R.string.rating, rating)
                tvVoteCount.text = binding.root.context.getString(R.string.votes, voteCount)
                tvOverview.text = overview
                rvTrailers.apply {
                    adapter = trailerAdapter
                    trailerAdapter.submitList(trailers)
                }
            }
        }
    }

}