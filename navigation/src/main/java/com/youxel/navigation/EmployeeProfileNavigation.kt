package com.youxel.navigation

import android.app.Activity



interface EmployeeProfileNavigation {
    fun toEmployeeProfileActivity(activity: Activity, email: String)
}