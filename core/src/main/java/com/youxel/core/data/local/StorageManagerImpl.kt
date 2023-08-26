package com.youxel.core.data.local

import com.orhanobut.hawk.Hawk
import com.youxel.core.data.local.Config.ACCESS_TOKEN_KEY
import com.youxel.core.data.local.Config.COLOR_CUSTOMIZATION
import com.youxel.core.data.local.Config.DEPARTMENT_NAME
import com.youxel.core.data.local.Config.IS_USER_LOG_IN
import com.youxel.core.data.local.Config.NOTIFICATIONS_HUB_REGISTRATION_ID
import com.youxel.core.data.local.Config.REFRESH_TOKEN_KEY
import com.youxel.core.data.local.Config.USER_EMAIL
import com.youxel.core.data.local.Config.USER_NAME
import com.youxel.core.data.local.Config.USER_PROFILE_IMAGE
import javax.inject.Inject

/**
 * Created by Nader Nabil on 14/12/2020.
 */
class StorageManagerImpl @Inject constructor() : StorageManager {

    override var accessToken: String
        get() = Hawk.get(ACCESS_TOKEN_KEY, "")
        set(value) {
            Hawk.put(ACCESS_TOKEN_KEY, value)
        }
    override var refreshToken: String
        get() = Hawk.get(REFRESH_TOKEN_KEY, "")
        set(value) {
            Hawk.put(REFRESH_TOKEN_KEY, value)
        }
    override var isLogin: Boolean
        get() = Hawk.get(IS_USER_LOG_IN, false)
        set(value) {
            Hawk.put(IS_USER_LOG_IN, value)
        }

    override var userProfileImage: String
        get() = Hawk.get(USER_PROFILE_IMAGE, "")
        set(value) {
            Hawk.put(USER_PROFILE_IMAGE, value)
        }
    override var userName: String
        get() = Hawk.get(USER_NAME, "")
        set(value) {
            Hawk.put(USER_NAME, value)
        }
    override var departmentName: String
        get() = Hawk.get(DEPARTMENT_NAME, "")
        set(value) {
            Hawk.put(DEPARTMENT_NAME, value)
        }

    override var email: String
        get() = Hawk.get(USER_EMAIL, "")
        set(value) {
            Hawk.put(USER_EMAIL, value)
        }
    override var notificationsHubRegistrationId: String
        get() = Hawk.get(NOTIFICATIONS_HUB_REGISTRATION_ID, "")
        set(value) {
            Hawk.put(NOTIFICATIONS_HUB_REGISTRATION_ID, value)
        }
    override var colorCustomization: AppTheme
        get() = Hawk.get(
            COLOR_CUSTOMIZATION,
            hashMapOf()
        )
        set(value) {
            Hawk.put(COLOR_CUSTOMIZATION, value)
        }

    override fun clearUserData() {
        Hawk.delete(ACCESS_TOKEN_KEY)
        Hawk.delete(REFRESH_TOKEN_KEY)
        Hawk.delete(IS_USER_LOG_IN)
        Hawk.delete(USER_PROFILE_IMAGE)
        Hawk.delete(USER_NAME)
        Hawk.delete(DEPARTMENT_NAME)
        Hawk.delete(USER_EMAIL)
        Hawk.delete(NOTIFICATIONS_HUB_REGISTRATION_ID)
    }
}