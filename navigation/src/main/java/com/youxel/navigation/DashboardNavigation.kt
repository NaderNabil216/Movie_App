package com.youxel.navigation

import android.app.Activity

/**
 * Created by Nader Nabil on 12/11/2020.
 */
interface DashboardNavigation {
    fun toDashBoard(activity: Activity)
    fun toTremvoHome(activity: Activity, accessToken: String)
    fun toTremvoLogin(activity: Activity)

    fun toMyDashBoard(activity: Activity)

    fun fromLoginToDashBoard(activity: Activity)

    fun toInbox(activity: Activity)

    fun toNewProfile(activity: Activity)

    fun toNews(activity: Activity)

    fun toAnnouncements(activity: Activity)

    fun toServices(activity: Activity)

    fun toAboutCerqel(activity: Activity)
}