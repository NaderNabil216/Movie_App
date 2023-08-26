package com.youxel.core.data.local

import com.orhanobut.hawk.Hawk
import javax.inject.Inject

/**
 * Created by Nader Nabil on 14/12/2020.
 */
class StorageManagerImpl @Inject constructor() : StorageManager {
    override fun clearUserData() {
        Hawk.deleteAll()
    }
}