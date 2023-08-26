package com.youxel.core.base.activity

import androidx.lifecycle.MutableLiveData

interface BottomNavListener {
    var hideBottomNavListener: MutableLiveData<Boolean>
}