package com.youxel.core.utils

import android.app.Activity
import android.view.WindowManager

/**
 * Created By Nader Nabil
 */

fun Activity.setFullScreen(){
    window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}


fun Activity.clearFullScreen(){
    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}

