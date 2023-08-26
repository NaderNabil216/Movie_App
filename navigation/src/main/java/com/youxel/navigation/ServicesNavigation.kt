package com.youxel.navigation

import android.app.Activity

/**
 * Created by Nader Nabil on 7/12/2020.
 */
interface ServicesNavigation {
    fun toServicesList(activity: Activity , serviceParentCategoryName:String , serviceCategoryId:String)
    fun toServiceForm(
        activity: Activity,
        serviceId: String,
        serviceParentCategoryName: String,
        serviceDescription: String
    )

    fun toFavoriteServicesList(activity: Activity, expendServiceId: String? = null)
}