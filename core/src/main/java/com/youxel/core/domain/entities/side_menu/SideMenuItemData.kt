package com.youxel.core.domain.entities.side_menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SideMenuItemData(
    @DrawableRes
    val itemIcon: Int,
    @StringRes
    val itemText: Int,
    val itemType: SideMenuItemType,
    @StringRes
    val headerTitle: Int? = null,
    var isSelected: Boolean = false
)

enum class SideMenuItemType {
    YOUXEL, TREMVO, CHECK
}