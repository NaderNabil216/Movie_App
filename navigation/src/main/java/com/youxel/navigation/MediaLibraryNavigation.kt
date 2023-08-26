package com.youxel.navigation

import android.app.Activity

interface MediaLibraryNavigation {
    fun toMediaLibrary(activity: Activity)
    fun toMediaDetails(activity: Activity, mediaId: String,fromGlobalSearch: Boolean = false)
}