package com.examplekia.myscholarship

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkStatus() {
    val TYPE_WIFI = 1
    val TYPE_MOBILE = 2
    val TYPE_NOT_CONNECTED = 3

    public fun isConnectNetwork(context : Context):Boolean{
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = manager.activeNetworkInfo?:null

        if(networkInfo == null){
            return false
        }else{
            return true
        }
    }
}