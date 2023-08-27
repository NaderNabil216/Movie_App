package com.youxel.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.google.android.material.card.MaterialCardView
import com.youxel.core.R
import com.youxel.core.domain.entities.enums.ToastType

/*
* Created By Nader Nabil.
*/
@SuppressLint("InflateParams")
fun Context.showToast(
    msg: String,
    toastType: ToastType = ToastType.DEFAULT,
    isLongDurationToast: Boolean = true,
) {
    val toast = Toast(this)
    val inflater: LayoutInflater =
        this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    val view: View = inflater.inflate(R.layout.custom_toast, null)
    val contentLayout: MaterialCardView = view.findViewById(R.id.contentLayout)
    val startIconImageView = view.findViewById<ImageView>(R.id.imgToast)
    val endIconImageView = view.findViewById<ImageView>(R.id.imgCloseToast)
    val toastText = view.findViewById<TextView>(R.id.msgToast)

    toastText.text = msg

    when (toastType) {
        ToastType.DEFAULT -> {
            applyToastStyle(
                this,
                contentLayout = contentLayout,
                contentLayoutBgColor = R.color.bg_toast,
                contentLayoutStrokeColor = R.color.stroke_color_toast,
                toastText = toastText,
                toastTextColor = R.color.rich_black,
                startIconImageView = startIconImageView,
                startIconDrawable = R.drawable.successfuly,
                endIconImageView = endIconImageView,
                endIconDrawable = R.drawable.default_close_outline
            )
        }

        ToastType.WARNING -> {
            applyToastStyle(
                this,
                contentLayout = contentLayout,
                contentLayoutBgColor = R.color.warning_toast_bg,
                contentLayoutStrokeColor = R.color.warning_toast_stroke,
                toastText = toastText,
                toastTextColor = R.color.warning_toast_stroke,
                startIconImageView = startIconImageView,
                startIconDrawable = R.drawable.ic_warning_toast,
                endIconImageView = endIconImageView,
                endIconDrawable = R.drawable.ic_warning_toast_close
            )
        }
    }

    toast.apply {
        duration = if (isLongDurationToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
        this.view = view
        show()
    }
}

private fun applyToastStyle(
    context: Context,
    contentLayout: MaterialCardView,
    @ColorRes contentLayoutBgColor: Int,
    @ColorRes contentLayoutStrokeColor: Int,
    toastText: TextView,
    @ColorRes toastTextColor: Int,
    startIconImageView: ImageView,
    @DrawableRes startIconDrawable: Int,
    endIconImageView: ImageView,
    @DrawableRes endIconDrawable: Int,
) {
    contentLayout.setCardBackgroundColor(context.getColor(contentLayoutBgColor))
    contentLayout.strokeColor = context.getColor(contentLayoutStrokeColor)

    toastText.setTextColor(context.getColor(toastTextColor))

    startIconImageView.loadImg(startIconDrawable)
    endIconImageView.loadImg(endIconDrawable)
}