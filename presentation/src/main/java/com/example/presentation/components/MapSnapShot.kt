package com.example.presentation.components

import android.content.Context
import android.content.Intent
import android.net.Uri


fun openGoogleMaps(context: Context, latitude: Double, longitude: Double) {
    val googleMapsUri = "geo:$latitude,$longitude"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsUri))
    intent.setPackage("com.google.android.apps.maps")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    // Verify that the Google Maps app is installed
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        // If Google Maps app is not installed, open the map in a web browser
        val mapUrl = "https://www.google.com/maps?q=$latitude,$longitude"
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl)))
    }
}

fun generateStaticMapUrl(latitude: Double, longitude: Double): String {
    val apiKey = "ee82972f82a64e56ad5c1b6d07badb23" // Replace with your Google Maps API Key
    val zoom = 14.3497
    val marker = "marker=lonlat:$longitude,$latitude;color:%23ff0000;size:medium" // %23 represents #
    return "https://maps.geoapify.com/v1/staticmap?style=osm-bright-smooth&width=600&height=400&center=lonlat%3A$longitude%2C$latitude&zoom=$zoom&$marker&apiKey=$apiKey"
}

fun extractLatLongFromUrl(url: String): Pair<Double, Double>? {
    val regex = Regex("""lonlat:(-?\d+\.\d+),(-?\d+\.\d+)""")
    val matchResult = regex.find(url)
    return matchResult?.let { result ->
        val latitude = result.groupValues[1].toDouble()
        val longitude = result.groupValues[2].toDouble()
        latitude to longitude
    }
}


