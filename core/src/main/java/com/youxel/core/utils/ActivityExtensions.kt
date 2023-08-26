package com.youxel.core.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager

fun <T> Activity.restartApp(startActivity: Class<T>) {
    val restartApp = Intent(this, startActivity)
    restartApp.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    restartApp.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    restartApp.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
    finish()
    startActivity(restartApp)
    overridePendingTransition(0, 0)
}
fun Activity.getVersionName(): String = try {
    packageManager?.getPackageInfo(packageName, 0)?.versionName ?: ""
} catch (e: PackageManager.NameNotFoundException) {
    ""
}