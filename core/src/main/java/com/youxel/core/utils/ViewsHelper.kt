package com.youxel.core.utils

import VerticalImageSpan
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import com.youxel.core.R
import com.youxel.core.data.local.StorageManagerImpl
import com.youxel.core.domain.entities.enums.ToastType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun getDrawableBackground(context: Context, @DrawableRes drawableId: Int): Drawable {
    return ContextCompat.getDrawable(context, drawableId)!!
}

fun View.setMargins(
    leftMarginDp: Int? = null,
    topMarginDp: Int? = null,
    rightMarginDp: Int? = null,
    bottomMarginDp: Int? = null
) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        leftMarginDp?.run { params.leftMargin = this.dpToPx(context) }
        topMarginDp?.run { params.topMargin = this.dpToPx(context) }
        rightMarginDp?.run { params.rightMargin = this.dpToPx(context) }
        bottomMarginDp?.run { params.bottomMargin = this.dpToPx(context) }
        requestLayout()
    }
}

fun Int.dpToPx(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
}

fun ImageView.loadImg(imgUrl: String, placeHolder: Int? = droidninja.filepicker.R.drawable.image_placeholder) {

    val token = StorageManagerImpl().accessToken
    val glideUrl = imgUrl.takeIf { it.isNotEmpty() }?.run {
        GlideUrl(imgUrl) { mapOf(Pair("Authorization", "Bearer $token")) }
    } ?: emptyText()

    val glideImg = Glide.with(context)
        .load(glideUrl)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    placeHolder?.run {
        glideImg.placeholder(placeHolder)
            .error(placeHolder)
            .fallback(placeHolder)
    }

    glideImg.into(this)
}

fun ImageView.loadImg(drawableId: Int, placeHolder: Int? = droidninja.filepicker.R.drawable.image_placeholder) {

    val glideImg = Glide.with(context)
        .load(drawableId)

    placeHolder?.run {
        glideImg.placeholder(placeHolder)
            .error(placeHolder)
            .fallback(placeHolder)
    }

    glideImg.into(this)
}

fun ViewPager2.setShowSideItems(pageMarginPx: Int, offsetPx: Int) {

    clipToPadding = false
    clipChildren = false
    offscreenPageLimit = 3

    setPageTransformer { page, position ->

        val offset = position * -(2 * offsetPx + pageMarginPx)
        if (this.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                page.translationX = -offset
            } else {
                page.translationX = offset
            }
        } else {
            page.translationY = offset
        }
    }

}

fun View.getColor(@ColorRes colorName: Int): Int {
    return ContextCompat.getColor(context, colorName)
}

fun TextView.setClickableHtmlText(htmlText: String) {
    movementMethod = LinkMovementMethod.getInstance()
    setHtmlText(htmlText)
}

fun TextView.setHtmlText(htmlText: String) {
    text =
        HtmlCompat.fromHtml(
            htmlText,
            HtmlCompat.FROM_HTML_MODE_LEGACY,
            GlideImageGetter(this),
            null
        )
}

fun View.setupFullHeightBottomSheet() {
    this.apply {
        val layoutParams = this.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        this.layoutParams = layoutParams
    }
}

fun ViewPager2.resetOtherPages() {
    (children.first { it is RecyclerView } as RecyclerView).run {

        val itemMargin = 0
        val otherPagesWidth = 0

        addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.left = itemMargin
                outRect.right = itemMargin
            }
        })

        clipToPadding = false

        setPadding(
            otherPagesWidth + itemMargin, 0,
            otherPagesWidth + itemMargin, 0
        )
    }
}

fun ViewPager2.showOtherPages() {
    (children.first { it is RecyclerView } as RecyclerView).run {

        val itemMargin = context.resources.getDimensionPixelOffset(R.dimen.x6dp)
        val otherPagesWidth = context.resources.getDimensionPixelOffset(R.dimen.x32dp)

        val isEnglish = LanguageUtils.getCurrentLanguage(this.context) == LanguageUtils.ENGLISH_CODE
        addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.left = if (isEnglish) 0 else itemMargin
                outRect.right = if (isEnglish) itemMargin else 0
            }
        })

        clipToPadding = false

        setPadding(
            0, 0,
            otherPagesWidth + itemMargin, 0
        )
    }
}


fun ViewPager2.addCompositePageTransformer() {
    // You need to retain one page on each side so that the next and previous items are visible
    this.offscreenPageLimit = 2
    // Add a PageTransformer that translates the next and previous items horizontally
    // towards the center of the screen, which makes them visible
    val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
    val currentItemHorizontalMarginPx =
        resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
    val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
    val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
        if (LanguageUtils.getCurrentLanguage(this.context) == LanguageUtils.ENGLISH_CODE) {
            page.translationX = -pageTranslationX * position
        } else {
            page.translationX = pageTranslationX * position
        }
        // Next line scales the item's height. You can remove it if you don't want this effect
//        page.scaleY = 1 - (0.25f * abs(position))
        // If you want a fading effect uncomment the next line:
//             page.alpha = 0.25f + (1 - abs(position))
    }
    this.setPageTransformer(pageTransformer)

    // The ItemDecoration gives the current (centered) item horizontal margin so that
    // it doesn't occupy the whole screen width. Without it the items overlap
    val itemDecoration = HorizontalMarginItemDecoration(
        this.context,
        R.dimen.viewpager_current_item_decoration_margin
    )
    this.addItemDecoration(itemDecoration)
}

fun CheckBox.setCustomStateIcon(context: Context, checkedIcon: Int, uncheckedIcon: Int) {
    val buttonState = StateListDrawable()
    buttonState.addState(
        intArrayOf(android.R.attr.state_checked),
        ContextCompat.getDrawable(context, checkedIcon)
    )
    buttonState.addState(
        intArrayOf(android.R.attr.state_focused),
        ContextCompat.getDrawable(context, uncheckedIcon)
    )
    buttonState.addState(
        intArrayOf(),
        ContextCompat.getDrawable(context, uncheckedIcon)
    )
    this.buttonDrawable = buttonState
}

fun CheckBox.applyCheckBoxButtonStyle(context: Context) {
    val buttonState = StateListDrawable()
    buttonState.addState(
        intArrayOf(android.R.attr.state_checked),
        ContextCompat.getDrawable(context, R.drawable.checkbox_checked)
    )
    buttonState.addState(
        intArrayOf(android.R.attr.state_focused),
        ContextCompat.getDrawable(context, R.drawable.checkbox_unchecked)
    )
    buttonState.addState(
        intArrayOf(),
        ContextCompat.getDrawable(context, R.drawable.checkbox_unchecked)
    )
    this.buttonDrawable = buttonState

}

fun CheckBox.applyFilterCheckBoxStyle(context: Context) {
    this.layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    this.setMargins(0, 10, 0, 10)
    this.setPadding(32, 0, 32, 0)
    applyCheckBoxButtonStyle(context)
    this.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
    this.setTextColor(ContextCompat.getColor(context, R.color.black))
    val typeface = Typeface.createFromAsset(context.assets, "poppins_regular.ttf")
    this.setTypeface(typeface, Typeface.NORMAL)
    this.textSize = 16f
}

fun RadioButton.applyRadioButtonState(context: Context) {
    val buttonState = StateListDrawable()
    buttonState.addState(
        intArrayOf(android.R.attr.state_checked),
        ContextCompat.getDrawable(context, R.drawable.radio_checked)
    )
    buttonState.addState(
        intArrayOf(android.R.attr.state_focused),
        ContextCompat.getDrawable(context, R.drawable.radio_unchecked)
    )
    buttonState.addState(
        intArrayOf(),
        ContextCompat.getDrawable(context, R.drawable.radio_unchecked)
    )
    this.buttonDrawable = buttonState
}

fun RadioButton.applyFilterRadioButtonStyle(context: Context) {
    this.layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    this.setMargins(0, 10, 0, 10)
    this.setPadding(32, 0, 32, 0)
    this.applyRadioButtonState(context)

    this.textAlignment = View.TEXT_ALIGNMENT_TEXT_START

    this.setTextColor(ContextCompat.getColor(context, R.color.black))
    val typeface = Typeface.createFromAsset(context.assets, "poppins_regular.ttf")
    this.setTypeface(typeface, Typeface.NORMAL)
    this.textSize = 16f
}

fun TextView.addImageAtEnd(
    atText: String,
    @DrawableRes imgSrc: Int,
    imgWidth: Int,
    imgHeight: Int
) {
    val ssb = SpannableStringBuilder(atText.plus("  "))

    val drawable = ContextCompat.getDrawable(this.context, imgSrc) ?: return
    drawable.mutate()
    drawable.setBounds(
        0, 0,
        imgWidth,
        imgHeight
    )
    ssb.setSpan(
        VerticalImageSpan(drawable),
        ssb.length - 1,
        ssb.length,
        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
    )
    this.setText(ssb, TextView.BufferType.SPANNABLE)
}


fun Bitmap.circlize(): Bitmap {
    val output: Bitmap = Bitmap.createBitmap(
        width,
        height, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(output)
    val color = -0xbdbdbe
    val paint = Paint()
    val rect = Rect(0, 0, width, height)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
    canvas.drawCircle(
        width / 2f, height / 2f,
        width / 2f, paint
    )
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)
    return output
}

fun EditText.doAfterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                afterTextChanged.invoke(editable.toString())
            }
        }
    })
}

fun Chip.onStateChangedListener(onStateChangeListener: (Boolean, Int) -> Unit) {
    this.setOnCheckedChangeListener { _, isChecked ->
        onStateChangeListener(isChecked, this.id)
    }
}

fun Activity.openKeyboard() {
    currentFocus?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(this, InputMethodManager::class.java)
        inputMethodManager?.toggleSoftInputFromWindow(
            it.applicationWindowToken,
            InputMethodManager.SHOW_FORCED,
            0
        )
    }
}

fun View.hideSoftKeyboard() {
    this.let {
        val inputMethodManager =
            ContextCompat.getSystemService(this.context, InputMethodManager::class.java)
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

@SuppressLint("ClickableViewAccessibility")
fun View.hideKeyBoardOutSideTap() {
    this.setOnTouchListener { v, event ->
        v.hideSoftKeyboard()
        false
    }
}


fun TextInputLayout.initInstantSearchView(
    context: Context,
    getSearchResults: (String) -> Unit,
    clearSearchResults: () -> Unit,
    setSearchKeyword: (String) -> Unit
) {
    this.editText?.apply {
        doAfterTextChanged {
            if (it.startsWith(" ")) {
                this.setText("")
            }
            if (it.trim().isNotEmpty() && it.length >= 3) {
                setSearchKeyword(it)
                getSearchResults(it)
            }
            if (it.trim().isEmpty()) {
                setSearchKeyword("")
                getSearchResults("")
            }
        }

        setOnEditorActionListener { _, actionId, event ->
            if (event == null || event.action != KeyEvent.ACTION_DOWN)
                return@setOnEditorActionListener false

            if ((actionId == EditorInfo.IME_ACTION_SEARCH)
                && this.text.trim().length >= 3
            ) {
                clearSearchResults()
                setSearchKeyword(this.text.toString())
                getSearchResults(this.text.toString())
                hideKeyboard(context)
                return@setOnEditorActionListener true
            } else if (this.text.trim().length < 3) {
                context.let {
                    context.showToast(
                        msg = it.getString(R.string.enter_at_least_3_char),
                        toastType = ToastType.WARNING,
                        isLongDurationToast = false
                    )
                }
            }
            false
        }
    }
    this.setEndIconOnClickListener {
        this.editText?.setText("")
        clearSearchResults()
        setSearchKeyword("")
        getSearchResults("")
    }
}

fun TextInputLayout.initWithKeyboardActionSearchView(
    context: Context,
    getSearchResults: (String) -> Unit,
    clearSearchQuery: () -> Unit,

    ) {
    this.editText?.apply {
        setOnEditorActionListener { _, actionId, _ ->
            if ((actionId == EditorInfo.IME_ACTION_SEARCH) && this.text.trim().length >= 3) {
                getSearchResults(this.text.toString())
                hideKeyboard(context)
                return@setOnEditorActionListener true
            } else if (this.text.trim().length < 3) {
                context.let {
                    it.showToast(
                        msg = it.getString(R.string.enter_at_least_3_char),
                        toastType = ToastType.WARNING,
                        isLongDurationToast = false
                    )
                }
                return@setOnEditorActionListener true
            }
            false
        }
    }
    this.setEndIconOnClickListener {
        this.editText?.setText("")
        clearSearchQuery()
    }
}

fun TextInputLayout.initSearchUsersToInviteView(
    context: Context,
    getSearchResults: (String) -> Unit,
    clearSearchResults: () -> Unit,
    setSearchKeyword: (String) -> Unit
) {
    this.editText?.apply {
        doAfterTextChanged {
            if (it.trim().isNotEmpty() && it.length >= 3) {
                setSearchKeyword(it)
                getSearchResults(it)
            }
            if (it.trim().isEmpty()) {
                setSearchKeyword("")
                clearSearchResults()
            }
        }

        setOnEditorActionListener { _, actionId, _ ->
            if ((actionId == EditorInfo.IME_ACTION_SEARCH)
                && this.text.trim().length >= 3
            ) {
                setSearchKeyword(this.text.toString())
                getSearchResults(this.text.toString())
                hideKeyboard(context)
                return@setOnEditorActionListener true
            } else if (this.text.trim().length < 3) {
                context.let {
                    context.showToast(
                        it.getString(R.string.enter_at_least_3_char)
                    )
                }
            }
            false
        }
    }
    this.setEndIconOnClickListener {
        this.editText?.setText("")
        clearSearchResults()
        setSearchKeyword("")
    }
}

fun EditText.hideKeyboard(context: Context) {
    this.clearFocus()
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun EditText.showKeyboard(context: Context) {
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun EditText.observeFocusToShowKeyboard(context: Context) {
    this.onFocusChangeListener =
        View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                this.showKeyboard(context)
            } else {
                this.hideKeyboard(context)
            }
        }
}

fun View.getString(@StringRes stringResName: Int): String {
    return context.getString(stringResName)
}

fun TextView.setEmptyText() {
    text = ""
}

fun ImageView.circleImage(context: Context, image: Any) {
    Glide.with(context).load(image).apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun View.setSafeOnClickListener(
    defaultInterval: Int = 1000,
    onSafeClick: (View) -> Unit
) {
    val safeClickListener = SafeClickListener(defaultInterval) {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}