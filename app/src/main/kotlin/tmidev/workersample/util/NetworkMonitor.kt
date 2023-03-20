package tmidev.workersample.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}

class NetworkMonitorImpl @Inject constructor(
    @ApplicationContext context: Context
) : NetworkMonitor {
    private val connectivityManager = context.getSystemService<ConnectivityManager>()

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    override val isOnline: Flow<Boolean> = callbackFlow {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(connectivityManager.isConnected())
            }

            override fun onLost(network: Network) {
                trySend(connectivityManager.isConnected())
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                trySend(connectivityManager.isConnected())
            }
        }

        connectivityManager?.registerNetworkCallback(networkRequest, networkCallback)

        trySend(connectivityManager.isConnected())

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        }
    }.conflate()

    @Suppress("DEPRECATION")
    private fun ConnectivityManager?.isConnected(): Boolean {
        if (this == null) return false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) activeNetwork
            ?.let(::getNetworkCapabilities)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        else activeNetworkInfo?.isConnected ?: false
    }
}