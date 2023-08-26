package com.youxel.core.utils

import android.os.Build

object VersionUtils {

    val isMarshmallowAndLater: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    val isOreoAndLater: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
}
