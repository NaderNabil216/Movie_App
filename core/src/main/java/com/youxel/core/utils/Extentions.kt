package com.youxel.core.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.youxel.core.R
import java.io.File
import java.io.Serializable
import java.util.Collections

fun Bundle.putEnum(key: String, enum: Enum<*>) {
    this.putString(key, enum.name)
}

inline fun <reified T : Enum<T>> Bundle.getEnum(key: String): T {
    return enumValueOf(getString(key) ?: "")
}

fun View.showPopup(menuRes: Int, onItemClickAction: (Int) -> Boolean) {
    val popup = PopupMenu(this.context, this)
    popup.setOnMenuItemClickListener {
        onItemClickAction(it.itemId)
    }
    val inflater = popup.menuInflater
    inflater.inflate(menuRes, popup.menu)
    popup.show()
}

fun Fragment.showDialog(dialog: DialogFragment) {
    dialog.show(childFragmentManager, dialog::class.java.name)
}

fun Fragment.getIntent(): Intent? {
    return requireActivity().intent
}

inline fun <reified T> Any.mapTo(defaultValue: T): T =
    try {
        GsonBuilder().create().run {
            fromJson(toJson(this@mapTo), T::class.java)
        }
    } catch (e: Exception) {
        defaultValue
    }


inline fun <T, R> T.convertTo(transform: (T) -> R): R {
    return transform(this)
}

inline fun <reified T> List<T>.copyList(): ArrayList<T> {
    val gsonList = Gson().toJson(this)
    return Gson().fromJson<ArrayList<T>>(gsonList)
}

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

fun getEmptyString(): String {
    return ""
}

fun Any.toJson(): String = Gson().toJson(this)

inline fun <reified T> String.fromJson(defaultValue: T): T =
    try {
        GsonBuilder().setLenient().create().run {
            fromJson(this@fromJson, object : TypeToken<T>() {}.type)
        }
    } catch (e: Exception) {
        defaultValue
    }


fun String.decodeBase64ToBitmap(): Bitmap? {
    var modifiedString = this
    if (this.contains("data:image/png;base64,")) {
        modifiedString = removePrefix("data:image/png;base64,")
    }
    val imageBytes = Base64.decode(modifiedString, Base64.DEFAULT)
    return try {
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    } catch (e: Exception) {
        null
    }
}


fun String.extractUserNameInitials(charsNumbers: Int): String {
    return try {
        // if display name contains any special chars, get only the first char
        when {
            this.contains(' ') -> {
                this
                    .split(' ')
                    .mapNotNull { it.firstOrNull()?.toString() }
                    .reduce { acc, s -> acc + s }
                    .slice(0..1)
                    // Add spaces between chars to avoid concatination in Arabic names
                    .replace("", " ").trim()
            }

            this.contains(Regex("[^\\w\\u0621-\\u064A\\s]")) -> {
                take(1)
            }

            else -> {
                this.take(charsNumbers).replace("", " ").trim()
            }
        }
    } catch (e: Exception) {
        this.take(1)
    }
}


fun String.getStringFromHtml(): String {
    return HtmlCompat.fromHtml(
        this,
        HtmlCompat.FROM_HTML_MODE_LEGACY,
    ).toString()
}


fun Activity.hideSoftKeyboard() {
    currentFocus?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Activity.createImageFileInCacheStorage(): Uri {
    val pathname = "${this.cacheDir}"
    val folder = File(pathname)
    folder.mkdirs()
    val file = File(folder, "${System.currentTimeMillis()}.jpg")
    file.createNewFile()
    return file.toUri()
}

fun <T> MutableList<T>.replace(oldItem: T, newItem: T) {
    Collections.replaceAll(this, oldItem, newItem)
}


fun Activity.getTempFileUri(): Uri {
    val tempFile =
        File.createTempFile(System.currentTimeMillis().toString(), ".jpg", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

    return FileProvider.getUriForFile(
        applicationContext,
        "${this.packageName}.provider",
        tempFile
    )
}

fun String.buildUrl(): String {
    return if (this.contains("http")) {
        this
    } else {
        "https://".plus(this)
    }
}

fun <T : Serializable?> Intent.getSerializable(key: String, m_class: Class<T>): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this.getSerializableExtra(key, m_class)!!
    else
        this.getSerializableExtra(key) as T
}
