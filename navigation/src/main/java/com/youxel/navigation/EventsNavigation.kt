package com.youxel.navigation

import android.app.Activity


interface EventsNavigation {
    fun toEventsList(activity: Activity)
    fun toEventsListSearch(activity: Activity, eventId: String, fromGlobalSearch: Boolean = false)

    fun toEventDetails(activity: Activity, eventId: String, fromGlobalSearch: Boolean = false)
}