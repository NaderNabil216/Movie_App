package com.example.movies_listing.ui.activity

import com.example.movies_listing.R
import com.youxel.core.base.activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListingActivity:BaseActivity() {
    override var navGraphResourceId: Int = R.navigation.movies_listing_nav

}