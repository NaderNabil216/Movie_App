package com.youxel.core.utils

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.util.Log
import android.webkit.URLUtil
import androidx.core.content.ContextCompat
import com.youxel.core.R


fun Context.makeCall(activity: Activity, phoneNumber: String, callPhonePermissionRequestCode: Int) {
    val callIntent = Intent(Intent.ACTION_CALL)
    callIntent.data = Uri.parse("tel:$phoneNumber")
    if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        )
        != PackageManager.PERMISSION_GRANTED
    ) {

        activity.requestPermissions(
            arrayOf(Manifest.permission.CALL_PHONE),
            callPhonePermissionRequestCode
        )

    } else {
        startActivity(callIntent)
    }
}

fun Context.sendMail(emailAddress: String) {
    val mIntent = Intent(Intent.ACTION_SEND)
    mIntent.data = Uri.parse("mailto:")
    mIntent.type = "text/plain"
    mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
    startActivity(mIntent)

}

fun Context.startBrowserIntent(url: String) {
    if (!URLUtil.isValidUrl(url)) {
        return
    }
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    this.startActivity(i)
}

fun Context.addToContactNumber(
    name: String,
    mobile: String,
    mobile2: String,
    phone: String,
    emailAddress: String
) {
    val intent = Intent(Intent.ACTION_INSERT)
    intent.type = ContactsContract.Contacts.CONTENT_TYPE
    intent.putExtra(ContactsContract.Intents.Insert.NAME, name)
    intent.putExtra(ContactsContract.Intents.Insert.PHONE, mobile)
    intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, mobile2)
    intent.putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE, phone)
    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, emailAddress)
    startActivity(intent)
}

fun Context.launchShareIntent(shareUrl: String, title: String = "", message: String = "") {
    val intent = Intent(Intent.ACTION_SEND)
    intent.apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_TITLE, title,
        )
        putExtra(Intent.EXTRA_TEXT, shareUrl)
    }
    this.startActivity(Intent.createChooser(intent, this.getString(R.string.share_using)))
}

fun Context.addEventToCalendar(
    eventStartTimeMilliSeconds: Long,
    eventEndTimeMilliSeconds: Long,
    eventTitle: String,
    eventLocation: String,
    isAllDAY: Boolean
) {
    val intent = Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, eventStartTimeMilliSeconds)
        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, eventEndTimeMilliSeconds)
        .putExtra(CalendarContract.Events.TITLE, eventTitle)
        .putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocation)
        .putExtra(CalendarContract.Events.ALL_DAY, isAllDAY)
        .putExtra(
            CalendarContract.Events.AVAILABILITY,
            CalendarContract.Events.AVAILABILITY_BUSY
        )
    startActivity(intent)

}

fun Context.openApplication(webLink: String, androidUrl: String) {
    if (androidUrl.isNotEmpty()) {
        val packageName = androidUrl.split("?id=").lastOrNull()
        try {
            val launchIntent = packageName?.let { packageManager.getLaunchIntentForPackage(it) }
            startActivity(launchIntent)

        } catch (e: Exception) {
            startBrowserIntent(androidUrl)
        }
    } else {
        startBrowserIntent(webLink)
    }
}

fun Context.watchYoutube(id: String) {
    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
    val webIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://youtu.be/$id")
    )
    try {
        this.startActivity(appIntent)
    } catch (ex: ActivityNotFoundException) {
        this.startActivity(webIntent)
    }
}
