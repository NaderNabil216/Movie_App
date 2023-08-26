package com.youxel.navigation

import android.app.Activity

/**
 * Created by Nader Nabil on 1/11/2020.
 */
interface NewsNavigation {
    fun toNewsList(activity: Activity)
    fun toNewsDetails(activity: Activity, newsId: String, fromGlobalSearch: Boolean = false)
}