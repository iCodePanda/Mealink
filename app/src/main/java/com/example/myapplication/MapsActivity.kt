package com.example.myapplication

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapComposable() {
    val context = LocalContext.current
    var map: GoogleMap? by remember { mutableStateOf(null) }
    val mapView = remember { MapView(context).apply { onCreate(Bundle()) } }
    val locationPermission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    DisposableEffect(key1 = mapView) {
        mapView.onStart()
        mapView.onResume()
        onDispose {
            mapView.onPause()
            mapView.onStop()
            mapView.onDestroy()
        }
    }

    LaunchedEffect(key1 = true) {
        locationPermission.launchPermissionRequest()
    }

    AndroidView({ mapView }, modifier = Modifier.fillMaxSize()) { mapView ->
        mapView.getMapAsync { googleMap ->
            map = googleMap
            if (locationPermission.status.isGranted) {
                try {
                    googleMap.isMyLocationEnabled = true
                    googleMap.uiSettings.isMyLocationButtonEnabled = true
                    // Default location - update accordingly
                    val defaultLocation = LatLng(-33.852, 151.211)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
                } catch (e: SecurityException) {
                    Log.e("MapComposable", "Location permission not granted", e)
                }
            }
        }
    }
}

