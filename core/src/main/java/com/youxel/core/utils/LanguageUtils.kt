package com.youxel.core.utils

import android.content.Context

/**
 * Created By Nader Nabil
 */

class LanguageUtils {
    companion object {

        const val ENGLISH_CODE = "en"
        const val ARABIC_CODE = "ar"

        fun getCurrentLanguage(
            context: Context,
            defaultLanguage: String = LocaleUtils.ENGLISH_LANG_CODE
        ): String {
            return LocaleUtils.getPersistedData(context, Constants.ENGLISH_CODE) ?: defaultLanguage
        }

        fun changeAppLanguage(context: Context) {
            val appLanguage = LocaleUtils.getPersistedData(context, Constants.ENGLISH_CODE)
            if (appLanguage == ENGLISH_CODE) {
                LocaleUtils.setLocale(context, LocaleUtils.ARABIC_LANG_CODE)
            } else {
                LocaleUtils.setLocale(context, LocaleUtils.ENGLISH_LANG_CODE)
            }
        }

        fun setLocale(context: Context) {
            val appLanguage = getCurrentLanguage(context)
            if (appLanguage == ENGLISH_CODE) {
                LocaleUtils.setLocale(context, LocaleUtils.ENGLISH_LANG_CODE)
            } else {
                LocaleUtils.setLocale(context, LocaleUtils.ARABIC_LANG_CODE)
            }
        }

        fun isEnglishLanguage(context: Context): Boolean {
            return getCurrentLanguage(context) == ENGLISH_CODE
        }
    }
}