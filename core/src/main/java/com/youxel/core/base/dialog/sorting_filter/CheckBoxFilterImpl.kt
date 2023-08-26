package com.youxel.core.base.dialog.sorting_filter

import android.content.Context
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import com.youxel.core.domain.entities.base.SortingFilter
import com.youxel.core.utils.applyFilterCheckBoxStyle


class CheckBoxFilterImpl : FilterInflater {

    private val selectedIndexes = mutableListOf<Int>()
    private val checkBoxesList = mutableListOf<CheckBox>()


    override fun initSortingFilter(
        context: Context, parentView: ViewGroup, sortingFilter: SortingFilter
    ) {
        setupCheckBoxes(
            context,
            parentView as LinearLayout,
            sortingFilter.resFiltersList,
            sortingFilter.lastSelectedFilterIndexes
        )
    }

    private fun setupCheckBoxes(
        context: Context,
        parentView: LinearLayout,
        filtersList: Array<String>,
        lastSelectedIndexes: List<Int>
    ) {
        filtersList.forEachIndexed { index, filter ->
            val checkBox = CheckBox(context)
            checkBox.text = filter
            checkBox.id = index
            if (lastSelectedIndexes.contains(index)) {
                checkBox.isChecked = true
            }
            checkBox.applyFilterCheckBoxStyle(context)
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                run {
                    if (isChecked) {
                        selectedIndexes.add(index)
                    } else {
                        selectedIndexes.remove(index)
                    }
                }
            }
            checkBoxesList.add(checkBox)
            parentView.addView(checkBox)
        }
    }
}