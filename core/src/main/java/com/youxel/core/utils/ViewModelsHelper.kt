package com.youxel.core.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

/*
*  Created by Shehab Elsarky.
*/
fun <T : ViewModel> Fragment.setSharedViewModel(
    owner: ViewModelStoreOwner, viewModelClass: Class<T>, viewModelFactory: ViewModelProvider.Factory
) = ViewModelProvider(owner, viewModelFactory).get(viewModelClass)