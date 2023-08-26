package com.youxel.core.custom_views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.*
import androidx.core.content.res.use
import com.google.android.material.textfield.TextInputLayout
import com.youxel.core.R

class CustomSpinner : FrameLayout {

    private lateinit var spinner: TextInputLayout

    constructor(context: Context) : super(ContextThemeWrapper(context, R.style.MaterialAppTheme)) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) :
            super(ContextThemeWrapper(context, R.style.MaterialAppTheme), attrs) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        spinner = LayoutInflater.from(context)
            .inflate(R.layout.custom_spinner, this, false) as TextInputLayout


        context.obtainStyledAttributes(attrs, R.styleable.CustomSpinner).use {
            spinner.editText?.setTextColor(
                it.getColor(
                    R.styleable.CustomSpinner_android_textColor,
                    spinner.editText!!.currentTextColor
                )
            )

            setText(it.getString(R.styleable.CustomSpinner_android_text) ?: "")


            spinner.editText?.textSize = it.getDimension(
                R.styleable.CustomSpinner_android_textSize,
                16f
            )

            spinner.setEndIconTintList(
                ColorStateList.valueOf(
                    it.getColor(
                        R.styleable.CustomSpinner_spinnerIconTint,
                        Color.BLACK
                    )
                )

            )
        }

        addView(spinner)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        spinner.setEndIconOnClickListener(l)
        spinner.editText?.setOnClickListener(l)
    }

    
    fun <T> setAdapter(adapter: T) where T : ListAdapter, T : Filterable {
        (spinner.editText as AutoCompleteTextView).setAdapter(adapter)
    }

    fun setOnItemClickListener(listener: AdapterView.OnItemClickListener) {
        (spinner.editText as AutoCompleteTextView).onItemClickListener = listener
    }

    fun setText(text: String) {
        spinner.editText?.setText(text)
    }

    fun setText(textRes: Int) {
        spinner.editText?.setText(textRes)
    }


}