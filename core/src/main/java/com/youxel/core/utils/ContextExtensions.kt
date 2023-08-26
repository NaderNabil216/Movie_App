package com.youxel.core.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.provider.Settings
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import java.util.*

/**
 * Created by Nader Nabil on 5/11/2020.
 */


/**
 * Extension function to get int value of color from color resource.
 *
 * @receiver Context
 * @param resourceId Int the color res.
 * @return Int value of the color.
 */
fun Context.getColorCompat(@ColorRes resourceId: Int) = ContextCompat.getColor(this, resourceId)

/**
 * Extension function to get drawable from drawable resource.
 *
 * @receiver Context
 * @param resourceId Int the drawable res
 * @return Drawable? the instance of drawable
 */
fun Context.getDrawableCompat(@DrawableRes resourceId: Int) =
    ContextCompat.getDrawable(this, resourceId)

fun Context.getStringByLocale(
    @StringRes stringRes: Int,
    locale: Locale,
    vararg formatArgs: Any
): String {
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)
    return createConfigurationContext(configuration).resources.getString(stringRes, *formatArgs)
}

fun Context.getLocal(context: Context): Locale {
    return if (LocaleUtils.getPersistedData(
            context,
            Constants.ENGLISH_CODE
        ) == Constants.ENGLISH_CODE
    ) {
        Locale("en")
    } else {
        Locale("ar")
    }
}

fun Context.getBuildVersionName(): String {
    return try {
        packageManager.getPackageInfo(packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        ""
    }
}

fun Context.copyText(text: String, label: String? = "") {
    val myClipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val myClip: ClipData = ClipData.newPlainText(label, text)
    myClipboard.setPrimaryClip(myClip)
}


fun Context.openAppSettings() {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    intent.addCategory(Intent.CATEGORY_DEFAULT)
    intent.data = Uri.parse("package:" + this.packageName)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    this.startActivity(intent)
}
fun Context.readFileFromAssets(filePath: String): String {
    return resources.assets.open(filePath).bufferedReader().use {
        it.readText()
    }
}
