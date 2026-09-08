package com.example.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.coroutines.resume

data class LocationInfo(
    val latitude: Double,
    val longitude: Double,
    val cityName: String
)

object LocationHelper {

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(context: Context): LocationInfo? {
        val fusedClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        return try {
            // First attempt to get last known location
            val lastLocation = suspendCancellableCoroutine<Location?> { cont ->
                fusedClient.lastLocation
                    .addOnSuccessListener { loc ->
                        if (cont.isActive) cont.resume(loc)
                    }
                    .addOnFailureListener {
                        if (cont.isActive) cont.resume(null)
                    }
            }

            val validLoc = lastLocation ?: requestSingleFreshLocation(fusedClient)
            if (validLoc != null) {
                val cityName = getCityName(context, validLoc.latitude, validLoc.longitude)
                LocationInfo(validLoc.latitude, validLoc.longitude, cityName)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun requestSingleFreshLocation(client: FusedLocationProviderClient): Location? {
        return suspendCancellableCoroutine { cont ->
            val req = LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 5000)
                .setMaxUpdates(1)
                .build()

            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    client.removeLocationUpdates(this)
                    if (cont.isActive) cont.resume(result.lastLocation)
                }
            }

            client.requestLocationUpdates(req, callback, Looper.getMainLooper())
                .addOnFailureListener {
                    if (cont.isActive) cont.resume(null)
                }

            cont.invokeOnCancellation {
                client.removeLocationUpdates(callback)
            }
        }
    }

    suspend fun getCityName(context: Context, lat: Double, lon: Double): String = withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                var name = "My Location"
                geocoder.getFromLocation(lat, lon, 1) { addresses ->
                    val addr = addresses.firstOrNull()
                    if (addr != null) {
                        name = addr.locality ?: addr.subAdminArea ?: addr.adminArea ?: "My Location"
                    }
                }
                name
            } else {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(lat, lon, 1)
                val addr = addresses?.firstOrNull()
                addr?.locality ?: addr?.subAdminArea ?: addr?.adminArea ?: "My Location"
            }
        } catch (e: Exception) {
            "My Location"
        }
    }
}
