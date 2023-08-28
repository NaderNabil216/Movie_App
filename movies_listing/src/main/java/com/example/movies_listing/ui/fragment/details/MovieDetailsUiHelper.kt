package com.example.movies_listing.ui.fragment.details

import com.example.movies_listing.databinding.FragmentMovieDetailsBinding
import com.example.movies_listing.domain.entities.local.MovieDetails
import com.youxel.core.base.fragment.BaseUiHelper
import com.youxel.core.utils.loadImg
import javax.inject.Inject

class MovieDetailsUiHelper @Inject constructor(): BaseUiHelper() {
    fun setData(binding: FragmentMovieDetailsBinding,trailerAdapter: MovieDetailsTrailerAdapter,movieDetails: MovieDetails){
        binding.run {
            movieDetails.run {
                ivPoster.loadImg(posterImage)
                ivBackdrop.loadImg(backdropImage)
                tvMovieTitle.text=title
                tvReleaseDate.text=releaseDate
                tvVoteAverage.text= "rating: $rating"
                tvVoteCount.text="votes: $voteCount"
                tvOverview.text=overview
                rvTrailers.apply {
                    adapter=trailerAdapter
                    trailerAdapter.submitList(trailers)
                }
            }
        }
    }

    fun handleEmptyState(binding: FragmentMovieDetailsBinding){

    }

    fun handleInternetState(binding: FragmentMovieDetailsBinding,isConnected: Boolean){

    }
}