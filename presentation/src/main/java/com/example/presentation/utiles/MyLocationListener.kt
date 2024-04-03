package com.example.presentation.utiles

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle

class MyLocationListener(
    val locationManager: LocationManager,
    val callback: (Location) -> Unit
) : LocationListener {
    override fun onLocationChanged(location: Location) {
        callback(location)
        locationManager.removeUpdates(this)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
}
