package com.youxel.core.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Created by Shehab ELsarky
 */

/**
 * Single Livedata
 */
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun <T> MutableLiveData<MutableList<T>>.add(item: T) {
    val updatedItems: MutableList<T> = this.value ?: mutableListOf()
    if (updatedItems.contains(item)) {
        return
    } else {
        updatedItems.add(item)
    }
    this.value = updatedItems
}

fun <T> MutableLiveData<MutableList<T>>.remove(item: T) {
    try {
        val updatedItems: MutableList<T> = this.value ?: mutableListOf()
        updatedItems.remove(item)
        this.value = updatedItems
    } catch (e: Exception) {
        this.value = mutableListOf()
    }
}
