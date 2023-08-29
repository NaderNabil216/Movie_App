package com.example.movies_listing.ui.fragment.listing

import androidx.core.view.isVisible
import com.example.movies_listing.databinding.FragmentMoviesListingBinding
import com.youxel.core.base.fragment.BaseUiHelper
import javax.inject.Inject

class MoviesListingUiHelper @Inject constructor() : BaseUiHelper() {

    internal fun handleNoInternetConnectionView(
        binding: FragmentMoviesListingBinding,
        isConnected: Boolean
    ) {
        binding.run {
            pagingView.root.isVisible = isConnected
            noInternetView.root.isVisible = isConnected.not()
        }
    }
}