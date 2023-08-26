package com.youxel.navigation

import android.app.Activity

/**
 * Created by Shehab Elsarky.
 */
interface BasicInformationNavigation {
    fun toBasicInformationActivity(activity: Activity, data: Any)
    fun toBasicInformationActivity(activity: Activity)
    fun toContactDetailsActivity(activity: Activity)
}