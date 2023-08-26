package com.youxel.navigation

import android.app.Activity


interface InboxNavigation {
    fun toInboxList(activity: Activity)

    fun toMyRequestsList(activity: Activity)

    fun toInboxDetails(activity: Activity, requestId: String, taskId :String , isTask: Boolean)
}