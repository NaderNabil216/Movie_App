package com.youxel.core.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController

/*
*  Created By Nader Nabil.
*/
class FragmentExtensions {
    companion object {
        val FragmentManager.currentNavigationFragment: Fragment?
            get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()

        fun getCurrentNavigationComponentFragmentId(navController: NavController): Int? {
            return navController.currentDestination?.id
        }

        fun replaceFragment(
            supportFragmentManager: FragmentManager,
            @IdRes fragmentContainer: Int,
            fragment: Fragment,
            tag: String = "",
            isAddToBackStack: Boolean = false
        ) {
            if (isAddToBackStack) {
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(tag)
                    .commit()
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .replace(fragmentContainer, fragment)
                    .commit()
            }
        }
    }
}

fun Fragment.updateStatusBarColor(
    color: Int
) { // Color must be in hexadecimal format
    requireActivity().window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = color
    }
}

fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, ViewGroup.LayoutParams.MATCH_PARENT) }
    val percentWidth = rect.width() * percent
    requireDialog().window?.run {
        setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.MATCH_PARENT)

        if (LanguageUtils.getCurrentLanguage(requireContext()) == LanguageUtils.ENGLISH_CODE) {
            dialog?.window?.setGravity(Gravity.LEFT)
        } else {
            dialog?.window?.setGravity(Gravity.RIGHT)
        }
    }
}

fun Fragment.onBackPressed() {
    requireActivity().onBackPressedDispatcher.onBackPressed()
}
