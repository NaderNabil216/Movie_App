package com.example.movieapp.core

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
    }


}