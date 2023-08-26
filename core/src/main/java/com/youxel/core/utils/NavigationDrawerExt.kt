package com.youxel.core.utils

/*
import android.annotation.SuppressLint
import android.os.Build
import android.view.WindowInsets.Type.systemGestures
import androidx.drawerlayout.widget.DrawerLayout

@SuppressLint("NewApi") // Lint does not understand isAtLeastQ currently
fun DrawerLayout.shouldCloseDrawerFromBackPress(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // If we're running on Q, and this call to closeDrawers is from a key event
        // (for back handling), we should only honor it IF the device is not currently
        // in gesture mode. We approximate that by checking the system gesture insets
        return rootWindowInsets?.let {
            val systemGestureInsets = it.getInsets(systemGestures())
            return systemGestureInsets.left == 0 && systemGestureInsets.right == 0
        } ?: false
    }
    // On P and earlier, always close the drawer
    return true
}*/
