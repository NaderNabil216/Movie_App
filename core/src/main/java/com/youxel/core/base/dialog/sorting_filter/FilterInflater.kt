package com.youxel.core.base.dialog.sorting_filter

import android.content.Context
import android.view.ViewGroup
import com.youxel.core.domain.entities.base.SortingFilter
import com.youxel.core.domain.entities.base.SortingFilterResult

interface FilterInflater {
    fun initSortingFilter(
        context: Context, parentView: ViewGroup, sortingFilter: SortingFilter
    )
}