package com.youxel.core.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import java.util.*

/**
 * This class is used to change your application locale and persist this change for the next time
 * that your app is going to be used.
 * You can also change the locale of your application on the fly by using the setLocale method.
 *
 */
object LocaleUtils {

    var ARABIC_LANG_CODE = "ar"
    var ENGLISH_LANG_CODE = "en"

    private val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

    fun onAttach(context: Context): Context {
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang)
    }

    fun onAttach(context: Context, defaultLanguage: String): Context {
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context): String? {
        return getPersistedData(context, Locale.getDefault().language)
    }

    fun setLocale(context: Context, language: String?): Context {
        persist(context, language)

        return if (VersionUtils.isOreoAndLater) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)

    }

    fun getPersistedData(context: Context, defaultLanguage: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun persist(context: Context, language: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()

        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String?): Context {
        val locale = when(language){
            ARABIC_LANG_CODE->{
                Locale(language,"SA")
            }
            else->{
                Locale(language)
            }
        }
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

    @SuppressWarnings("deprecation")
    private fun updateResourcesLegacy(context: Context, language: String?): Context {
        val locale = Locale(language!!)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        context.resources.updateConfiguration(configuration, resources.displayMetrics)

        return context
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    fun getCountryCodeUsingLocals(context: Context): String {
        val locale: Locale
        if (VersionUtils.isOreoAndLater) {
            locale = context.resources.configuration.locales.get(0)
        } else {
            locale = context.resources.configuration.locale
        }
        return locale.country
    }


}