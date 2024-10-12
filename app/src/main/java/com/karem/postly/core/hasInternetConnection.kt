package com.karem.postly.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission


@RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
fun hasInternetConnection(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetworkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        return networkInfo.isConnected
    }
}