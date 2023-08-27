package com.youxel.core.utils.network

/**
 * Created By Nader Nabil.
 */
class NetworkUtils {

    companion object{
        fun isNetworkConnected(state : ConnectivityProvider.NetworkState): Boolean {
            return (state as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
        }
    }
}