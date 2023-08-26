package com.youxel.core.custom_views

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.viewbinding.ViewBinding
import com.youxel.core.R
import com.youxel.core.base.fragment.BaseUiHelper
import com.youxel.core.utils.getSpecificColor
import com.youxel.core.utils.setMargins


class CustomRadioButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val TEXT_SIZE = 16f
    private var mmTypeface: Typeface? = null

    private lateinit var rootLayout: LinearLayout
    private var optionItems = listOf<RadioButtonOptionItem>()
    private var switchListener: ((RadioButtonOptionItem) -> Unit)? = null

    @DrawableRes
    private var selectedTabColor: Int? = null

    @DrawableRes
    private var unselectedTabColor: Int? = null

    private val baseUiHelper = BaseUiHelper()

    init {
        initAttr(attrs)
        initViews()
    }

    private fun initAttr(attrs: AttributeSet?) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomRadioButton)
        val mTypeface: String? = typedArray.getString(R.styleable.CustomRadioButton_typeface)
        mmTypeface = if (!TextUtils.isEmpty(mTypeface)) {
            Typeface.createFromAsset(context.assets, mTypeface)
        } else {
            ResourcesCompat.getFont(context, R.font.poppins_semibold)
        }
        typedArray.recycle()
    }

    fun setSelectedTabDrawable(@DrawableRes selectedTabColor: Int) {
        this.selectedTabColor = selectedTabColor
    }

    fun setUnselectedTabDrawable(@DrawableRes unselectedTabColor: Int = R.color.colorPrimaryLight) {
        this.unselectedTabColor = unselectedTabColor
    }

    private fun initViews() {
        rootLayout = LayoutInflater.from(context)
            .inflate(R.layout.custom_radio_button_layout, this, false) as LinearLayout
        addView(rootLayout)
    }

    /**
     * @param selectedItemPosition 0 index
     */
    fun addOptionItems(
        items: List<RadioButtonOptionItem>,
        selectedItemPosition: Int? = null
    ) {
        optionItems = items
        rootLayout.weightSum = items.size.toFloat()
        items.forEach {
            rootLayout.addView(getTextView(it))
        }
        if (selectedItemPosition != null && selectedItemPosition <= items.size)
            selectCertainView(items[selectedItemPosition].tag)
        else
            selectCertainView(items[0].tag)

    }

    private fun getTextView(item: RadioButtonOptionItem): TextView {
        val tv = TextView(context)
        tv.apply {
            text = item.text
            tag = item.tag
            setOnClickListener(provideOnClickListener())
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                .also { gravity = Gravity.CENTER }.also { setPadding(4, 0, 4, 0) }
                .also { setMargins(0, 2, 0, 2) }
            layoutParams = params
            textSize = TEXT_SIZE
            typeface = mmTypeface
        }
        return tv
    }

    private fun provideOnClickListener(): OnClickListener {
        return OnClickListener {
            selectCertainView(it.tag.toString())
            switchListener?.invoke(getSelectedOptionItem(it.tag.toString()))
        }
    }


    private fun TextView.setSelectedTextView() {
        background =
            ContextCompat.getDrawable(
                context,
                selectedTabColor ?: R.drawable.custom_radio_button_selected_option
            )
    }

    private fun TextView.setUnSelectedTextView() {
        background = if (unselectedTabColor != null)
            ContextCompat.getDrawable(context, unselectedTabColor!!)
        else
            null

    }

    fun selectCertainView(
        viewTag: String
    ) {
        rootLayout.children.iterator().forEach {
            (it as TextView).setUnSelectedTextView()
        }
        rootLayout.findViewWithTag<TextView>(viewTag).setSelectedTextView()
    }

    private fun getSelectedOptionItem(itemTag: String): RadioButtonOptionItem {
        return optionItems.find { it.tag == itemTag }!!
    }

    fun addOnSwitchListener(listener: (RadioButtonOptionItem) -> Unit) {
        this.switchListener = listener
    }

}

data class RadioButtonOptionItem(val text: String, val tag: String)
