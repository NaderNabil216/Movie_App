package com.youxel.core.base.activity

import android.content.Context
import android.os.Bundle
import android.os.storage.StorageManager
import android.view.View
import android.widget.*
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.youxel.core.R
import com.youxel.core.base.fragment.BaseUiHelper
import com.youxel.core.utils.*
import com.youxel.navigation.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 *  Created by Shehab Elsarky
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseActivity : AppCompatActivity(), LoadingListener, ToolbarListener {
    private val TAG = BaseActivity::class.java.simpleName

    private lateinit var navFragment: NavHostFragment
    lateinit var navController: NavController

    abstract var navGraphResourceId: Int
    protected lateinit var bundle: Bundle

    @LayoutRes
    open var layoutRes = R.layout.activity_common


    @Inject
    lateinit var storageManager: StorageManager


    var toolbar: Toolbar? = null
    var tvTitle: TextView? = null
    var tvBack: TextView? = null
    var ivImageBack: ImageView? = null
    var sideMenuContainer: FrameLayout? = null
    private var imgProfile: ImageView? = null
    private var llNavigationGroup: LinearLayout? = null
    private var imgSearch: ImageView? = null
    private var imgNotification: ImageView? = null
    private var progressBar: LinearLayout? = null
    private var watchAnimation: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!VersionUtils.isOreoAndLater) {
            LanguageUtils.setLocale(this)
        }
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        initViews()
        setNavFragment()
        setSupportActionBar(toolbar)
    }

    private fun initViews() {
        tvTitle = findViewById(R.id.tv_title)
        tvBack = findViewById(R.id.tvBack)
        imgProfile = findViewById(R.id.img_profile)
        ivImageBack = findViewById(R.id.ivImageBack)
        llNavigationGroup = findViewById(R.id.llBackGroup)
        imgSearch = findViewById(R.id.img_search)
        imgNotification = findViewById(R.id.imgNotification)
        toolbar = findViewById(R.id.toolbar)
        progressBar = findViewById(R.id.progressBar)
        watchAnimation = findViewById(R.id.watch_animation)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleUtils.onAttach(base))
    }


    private fun setNavFragment() {
        navFragment =
            supportFragmentManager.findFragmentById(R.id.common_nav_fragment) as NavHostFragment
        navController = navFragment.navController
        if (::bundle.isInitialized) navController.setGraph(navGraphResourceId, bundle)
        else navController.setGraph(navGraphResourceId)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            tvTitle?.text = destination.label
        }

        imgSearch?.setOnClickListener {}

        llNavigationGroup?.setOnClickListener { onBackPressed() }

        imgNotification?.setOnClickListener {}

    }



    override fun showActivityToolbar(isShowBack: Boolean?) {
        if (isShowBack == true) {
            showViews(llNavigationGroup)
            hideViews(
                imgProfile,
                imgNotification,
                imgSearch
            )
        } else {
            hideViews(llNavigationGroup)
            showViews(
                imgProfile,
                imgNotification,
                imgSearch
            )
        }
        toolbar?.visibility = View.VISIBLE
    }

    override fun toggleSearchIconToolbar(isShow: Boolean?) {
        imgSearch?.isVisible = isShow ?: false
    }

    private fun showViews(vararg visibleViews: View?) {
        visibleViews.map {
            it?.isVisible = true
        }
    }

    private fun hideViews(vararg hiddenViews: View?) {
        hiddenViews.map {
            it?.isVisible = false
        }
    }

    override fun hideActivityToolbar() {
        toolbar?.visibility = View.GONE
    }

    fun hideImageProfile() {
        imgProfile?.visibility = View.GONE
    }

    fun hideImageSearch() {
        imgSearch?.visibility = View.GONE
    }


    override fun loadUserProfileImage(userProfileImageUrl: String) {
        imgProfile?.loadImg(userProfileImageUrl, R.drawable.ic_profile_dummy)
    }

    override fun setActivityToolbarTitle(title: String, gravity: Int?) {
        tvTitle?.text = title
        gravity?.let {
            tvTitle?.gravity = it
        }
    }


    fun setFragmentDestination(@IdRes resId: Int, bundle: Bundle?) =
        navController.navigate(resId, bundle)

    fun popupFragment() = navController.popBackStack()

    override fun showLoading(show: Boolean) {
        progressBar?.visibility = if (show) View.VISIBLE else View.GONE
    }


}