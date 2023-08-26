package com.youxel.core.data.local

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FixedSizeStack<T>(private val sizeLimit: Int) {
    private val entries = mutableListOf<T>()

    fun push(item: T): Boolean {
        if (entries.size < sizeLimit) {
            if (entries.contains(item)) {
                entries.remove(item)
            }
            entries.add(item)
            return true
        } else if (entries.size == sizeLimit) {
            entries.removeFirst()
            if (entries.contains(item)) {
                entries.remove(item)
            }
            entries.add(item)
            return true
        }
        return false
    }

    fun pop(defaultValue: T): T =
        if (entries.isNotEmpty()) entries.removeAt(entries.lastIndex) else defaultValue

    fun peek(defaultValue: T): T =
        if (entries.isNotEmpty()) entries[entries.lastIndex] else defaultValue

    fun getEntries(entriesSize: Int? = null): List<T> = entries.run {
        val gsonList = Gson().toJson(this)
        val entriesList = Gson().fromJson<List<T>>(gsonList).asReversed()
        return entriesSize?.let { size ->
            entriesList.take(size)
        } ?: entriesList
    }

    fun removeItem(item: T): Boolean {
        if (entries.contains(item)) {
            entries.remove(item)
            return true
        }
        return false
    }

    fun clear() {
        entries.clear()
    }

    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)

}