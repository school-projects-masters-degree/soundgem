package com.example.soundgem

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.soundgem.supabase.AudioViewModel
import com.example.soundgem.ui.layouts.AppUI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity(), LocationListener {
    private val viewModel: AudioViewModel by viewModels()
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient



    // Register the permission callback
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Start location updates
                startLocationUpdates()
            } else {
                // Permission denied. Show an error message
                println("Permission denied")
            }
        }

    private fun startLocationUpdates() {
        try {
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            lastKnownLocation?.let {
                viewModel.updateCurrentLocation(it)
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                10f,
                this
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchData()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            AppUI(viewModel = viewModel)
        }


        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        // Check if the location permission is already available.
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ==
            android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            // Location permission has been granted
            startLocationUpdates()
            getLastLocation()
        } else {
            // Location permission has not been granted
            requestPermissionLauncher.launch(
                android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLastLocation() {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    location?.let {
                        viewModel.updateCurrentLocation(it)
                    }
                }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
    override fun onLocationChanged(location: Location) {
        Log.d("MainActivity", "Location updated: ${location.latitude}, ${location.longitude}")
        viewModel.updateLocation(location)
    }
}
