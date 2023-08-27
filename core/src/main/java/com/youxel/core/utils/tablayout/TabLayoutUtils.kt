package com.youxel.core.utils.tablayout

import android.view.View
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

/**
 *  Created By Nader Nabil
 */
class TabLayoutUtils @Inject constructor() {

    fun setTabView(tab: TabLayout.Tab, selectedView: View) {
        tab.customView = selectedView
    }
}