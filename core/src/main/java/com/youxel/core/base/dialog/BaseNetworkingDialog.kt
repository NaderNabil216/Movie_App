package com.youxel.core.base.dialog

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.google.android.material.snackbar.Snackbar
import com.youxel.core.R
import com.youxel.core.domain.entities.base.ErrorStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nader Nabil on 5/1/2021.
 */

class BaseNetworkingDialog @Inject constructor() {
    private lateinit var snackbar: Snackbar
    var isShown = false

    fun showDialog(context: Activity, errorStatus: ErrorStatus, errorMessage: String? = null) {

        if (isShown)
            return

        snackbar = Snackbar.make(context.window.decorView, "", Snackbar.LENGTH_LONG)

        initViews(context)

        isShown = true

        when (errorStatus) {
            ErrorStatus.NO_CONNECTION -> setTextErrorMsg(
                context,
                context.getString(R.string.no_internet_connection)
            )
            ErrorStatus.INTERNAL_SERVER_ERROR -> setTextErrorMsg(
                context,
                context.getString(R.string.internal_server_error)
            )
            ErrorStatus.UNKNOWN_HOST -> setTextErrorMsg(
                context,
                context.getString(R.string.unknown_host)
            )
            else->{
                setTextErrorMsg(context, errorMessage ?: "")
            }

        }
        snackbar.show()
    }


    fun dismiss(withDelay: Boolean = true) {
        isShown = false
        if (withDelay) CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            snackbar.dismiss()
        }
        else snackbar.dismiss()
    }

    private fun initViews(
        activity: Activity,
        msg: String = "",
        @DrawableRes placeHolder: Int? = null
    ) {
        snackbar = Snackbar.make(activity.window.decorView, "", Snackbar.LENGTH_LONG)
        val customSnackView: View =
            activity.layoutInflater.inflate(R.layout.custom_snackbar_view, null)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val view = snackbar.view
        val params = view.layoutParams as LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        val imgError: ImageView = customSnackView.findViewById(R.id.imgClose)
        val textError: TextView = customSnackView.findViewById(R.id.errorMessage)
        textError.text = msg
        placeHolder?.let {
            imgError.setImageResource(it)
        }
        snackbarLayout.addView(customSnackView, 0)
    }


    private fun setTextErrorMsg(context: Activity, msg: String) {
        initViews(context, msg)
    }

}