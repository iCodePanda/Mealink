import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.Offer
import com.example.myapplication.OfferDetailsScreen
import com.example.myapplication.OffersList
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.*


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapComposable(userLat: Any?, userLng: Any?, offers: SnapshotStateList<Offer>, onOfferSelected : (Offer?) -> Unit) {
    val context = LocalContext.current
    var map: GoogleMap? by remember { mutableStateOf(null) }
    val mapView = remember { MapView(context).apply { onCreate(Bundle()) } }
    val locationPermission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    var radius by remember { mutableStateOf(5f) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var radiusSet by remember { mutableStateOf(false) }
    var selectedOffer by remember { mutableStateOf<Offer?>(null) }

    DisposableEffect(key1 = mapView) {
        mapView.onStart()
        mapView.onResume()
        onDispose {
            mapView.onPause()
            mapView.onStop()
            mapView.onDestroy()
        }
    }

    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    LaunchedEffect(key1 = locationPermission.status.isGranted, key2 = radius) {
        locationPermission.launchPermissionRequest()
        if (locationPermission.status.isGranted) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    userLocation = LatLng(userLat as Double, userLng as Double)
                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10f))
                    map?.clear()
                    map?.addCircle(
                        CircleOptions()
                            .center(userLocation)
                            .radius((radius * 1000).toDouble())
                    )
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (!radiusSet) {
            AndroidView({ mapView }, modifier = Modifier.weight(1f)) { mapView ->
                mapView.getMapAsync { googleMap ->
                    map = googleMap
                    if (locationPermission.status.isGranted) {
                        map?.isMyLocationEnabled = true
                    }
                }
            }
            Text(
                text = "Radius: ${radius.toInt()} km",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
            Slider(
                value = radius,
                onValueChange = { radius = it },
                valueRange = 1f..10f,
                modifier = Modifier.padding(horizontal = 16.dp),
                colors = SliderDefaults.colors(
                    activeTrackColor = Color(0xFF00BF81),
                    thumbColor = Color(0xFF00BF81),
                    inactiveTrackColor = Color(0xFF00BF81).copy(alpha = 0.24f)
                )

            )
            Button(
                onClick = {
                    radiusSet = true
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF00BF81),
                    contentColor = Color.White
                )
            ) {
                Text("Set Radius")
            }
        }
        if (radiusSet) {
//            if (selectedOffer == null) {
                OffersList(offers = offers.filter { it.distance < radius }, onOfferSelected = {
                    selectedOffer = it
                    onOfferSelected(selectedOffer)
                })
            //}
//            else {
//                OfferDetailsScreen(selectedOffer = selectedOffer!!, onOfferSelected = { selectedOffer = it })
//            }
        }

    }
}


fun queryRestaurantsNearby(location: LatLng, radius: Float, map: GoogleMap?) {
    val db = FirebaseFirestore.getInstance()
    db.collection("restaurants")
        .whereEqualTo("type", "foodReceiver")
        .get()
        .addOnSuccessListener { documents ->
            documents.forEach { document ->
                val restaurantLat = document.getDouble("latitude") ?: return@forEach
                val restaurantLng = document.getDouble("longitude") ?: return@forEach
                val distance = calculateDistance(location.latitude, location.longitude, restaurantLat, restaurantLng)
                if (distance <= radius) {
                    val restaurantLocation = LatLng(restaurantLat, restaurantLng)
                    map?.addMarker(MarkerOptions().position(restaurantLocation).title(document.getString("name")))
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("MapComposable", "Error fetching documents: ", exception)
        }
}

fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
    val earthRadius = 6371 // kilometers
    val dLat = Math.toRadians(lat2 - lat1)
    val dLng = Math.toRadians(lng2 - lng1)
    val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLng / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return earthRadius * c
}
