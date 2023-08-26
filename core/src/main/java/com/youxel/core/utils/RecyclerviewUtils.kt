package com.youxel.core.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun getVerticalLayoutManager(context: Context): LinearLayoutManager {
    return LinearLayoutManager(context, RecyclerView.VERTICAL, false)
}