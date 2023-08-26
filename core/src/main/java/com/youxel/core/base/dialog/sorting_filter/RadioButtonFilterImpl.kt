package com.youxel.core.base.dialog.sorting_filter

import android.content.Context
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.youxel.core.domain.entities.base.SortingFilter
import com.youxel.core.domain.entities.base.SortingFilterResult
import com.youxel.core.utils.applyFilterRadioButtonStyle

class RadioButtonFilterImpl : FilterInflater {

    override fun initSortingFilter(
        context: Context, parentView: ViewGroup, sortingFilter: SortingFilter
    ) {
        setupRadioGroup(
            context,
            parentView as RadioGroup,
            sortingFilter.resFiltersList,
            sortingFilter.lastSelectedFilterIndexes[0]
        )
    }

    private fun setupRadioGroup(
        context: Context, radioGroup: RadioGroup, filtersList: Array<String>, selectedIndex: Int
    ) {
        filtersList.forEachIndexed { index, filter ->
            val radioButton = RadioButton(context)
            radioButton.text = filter
            radioButton.id = index
            if (index == selectedIndex) radioButton.isChecked = true
            radioButton.applyFilterRadioButtonStyle(context)
            radioGroup.addView(radioButton)
        }
    }

}