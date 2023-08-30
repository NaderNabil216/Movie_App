package com.youxel.core.base.fragment

import androidx.fragment.app.Fragment
import com.youxel.core.base.dialog.BaseNetworkingDialog
import com.youxel.core.domain.entities.base.ErrorStatus
import com.youxel.core.utils.network.ConnectivityProvider
import com.youxel.core.utils.network.NetworkUtils
import javax.inject.Inject

/**
 * Created By Nader Nabil
 */
open class NetworkBaseFragment : Fragment(), ConnectivityProvider.ConnectivityStateListener {

    private val TAG = NetworkBaseFragment::class.java.simpleName

    @Inject
    lateinit var baseNetworkingDialog: BaseNetworkingDialog

    companion object {
        var isNetworkConnected = true
    }

    private val provider: ConnectivityProvider by lazy {
        ConnectivityProvider.createProvider(
            requireContext()
        )
    }


    override fun onResume() {
        super.onResume()
        provider.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        if (baseNetworkingDialog.isShown) baseNetworkingDialog.dismiss()
        provider.removeListener(this)
    }

    override fun onNetworkStateChange(state: ConnectivityProvider.NetworkState) {
        val hasInternet = NetworkUtils.isNetworkConnected(state)
        isNetworkConnected = if (!hasInternet) {
            onInternetStatusChanged(false)
            false
        } else {
            onInternetStatusChanged(true)
            true
        }
    }

    open fun onInternetStatusChanged(isConnected:Boolean){}
}