package com.youxel.navigation

import android.app.Activity

/**
 * Created by Nader Nabil on 2/11/2020.
 */
interface AnnouncementsNavigation {
    fun toAnnouncementsList(activity: Activity)
    fun toAnnouncementDetails(
        activity: Activity,
        announcementId: String,
        fromGlobalSearch: Boolean = false
    )
}