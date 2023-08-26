package com.youxel.navigation

import android.app.Activity

interface CalendarNavigation {
    fun toCalendarActivity(activity: Activity)
    fun toCalendarDetailsActivity(activity: Activity, calanderEventId: String)
}