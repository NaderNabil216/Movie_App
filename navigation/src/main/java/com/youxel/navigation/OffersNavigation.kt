package com.youxel.navigation

import android.app.Activity
import androidx.fragment.app.FragmentManager

/**
 * Created by Nader Nabil on 24/11/2020.
 */
interface OffersNavigation {
    fun toOffersList(activity: Activity)
    fun toOfferDetails(activity: Activity, offerId: String, fromGlobalSearch: Boolean = false)
    fun toGenerateQrCode(
        fragmentManager: FragmentManager,
        title: String,
        expireDate: String,
        promoCode: String
    )
}