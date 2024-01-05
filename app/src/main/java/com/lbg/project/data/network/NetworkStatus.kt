package com.lbg.project.data.network

sealed class NetworkStatus {
    /** Device has a valid internet connection */
    data object Connected: NetworkStatus()
    /** Device has no internet connection */
    data object Disconnected: NetworkStatus()
}
