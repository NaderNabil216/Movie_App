package com.youxel.core.domain.utils

import com.google.gson.GsonBuilder

inline fun <reified T> Any.mapTo(): T? =
    try {
        GsonBuilder().create().run {
            fromJson(toJson(this@mapTo), T::class.java)
        }
    } catch (e: Exception) {
        null
    }


inline fun <reified T> Any.mapToNotNull(defaultValue: T): T =
    try {
        GsonBuilder().create().run {
            fromJson(toJson(this@mapToNotNull), T::class.java)
        }
    } catch (e: Exception) {
        defaultValue
    }

