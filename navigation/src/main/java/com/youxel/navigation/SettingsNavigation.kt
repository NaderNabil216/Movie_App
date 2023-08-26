package com.youxel.navigation

import android.app.Activity

interface SettingsNavigation {
    fun toSettings(activity: Activity)
    fun restartApp(activity: Activity)
}