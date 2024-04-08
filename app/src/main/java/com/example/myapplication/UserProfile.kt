package com.example.myapplication
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.*
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import org.json.JSONObject
private lateinit var auth: FirebaseAuth
private lateinit var storage: FirebaseStorage


object ProfileValidator {
    fun isValidName(name: String): Boolean = name.isNotEmpty()

    fun isValidEmail(email: String): Boolean =
        email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"))

    fun isValidLocation(location: String): Boolean = location.isNotEmpty()
}

@Composable
fun UserProfileScreen(navController: NavController) {
    auth = Firebase.auth
    storage = Firebase.storage
    var storageRef = storage.reference
    val user = auth.currentUser

    var isLoading by remember { mutableStateOf(true) }
    var failed by remember { mutableStateOf(false) }
    var userInfo by remember { mutableStateOf<Map<String, Any>?>(null) }
    var imageURI by remember { mutableStateOf("") }

    val docRef = user?.let { db.collection("users").document(it.uid) }
    docRef?.get()?.addOnSuccessListener { document ->
        if (document != null) {
            Log.d(TAG, "DocumentSnapshot data: ${document.data}")
            userInfo = document.data
            auth.currentUser?.let {
                storageRef.child(it.uid).downloadUrl.addOnSuccessListener {it ->
                    if (it != null) {
                        imageURI = it.toString()
                        isLoading = false
                    }
                }.addOnFailureListener {
                    isLoading = false
                }
            }
        } else {
            Log.d(TAG, "No such document")
            failed = true
        }
    }?.addOnFailureListener { exception ->
        Log.d(TAG, "get failed with ", exception)
        failed = true
    }


    if (isLoading) {
        LoadingScreen()
    } else if (!failed) {
            ProfileScreen(
                userInfo?.get("name").toString(),
                auth.currentUser?.email.toString(),
                userInfo?.get("location").toString(),
                userInfo?.get("type").toString(),
                imageURI,
                navController
            )
    } else {
        LoadFailScreen()
    }
}

@Composable
fun ProfileScreen(userName: String, userEmail: String, userLocation: String, type: String, imageURI: String, navController: NavController) {
    var name by remember { mutableStateOf(userName) }
    var email by remember { mutableStateOf(userEmail) }
    var location by remember { mutableStateOf(userLocation) }
    var interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {focusManager.clearFocus(true)}
            ),
        color = Color(0xFFF6F6F6)) {
        Scaffold(
            bottomBar = {
                NavBar(navController, type)
            },
        ) { inner ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 20.dp)
                    .padding(bottom = inner.calculateBottomPadding()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    if (type == "foodDonor") {
                        ExtendedFloatingActionButton(
                            onClick = {
                                navController.navigate(Screens.MyOffers.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            text = {Text("Manage My Offers")},
                            backgroundColor = Color(0xFF00BF81),
                            elevation = FloatingActionButtonDefaults.elevation(0.dp),
                            contentColor = Color(0xFFFFFFFF),
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Signout(name)
                }
                ProfileAccountHeader(type, name)
                ProfileUserPfp(imageURI)
                ProfileNameField(name = name, onNameChange = { name = it })
                ProfileEmailField(email = email, onEmailChange = { email = it })
                ProfileLocationField(location = location, onLocationChange = { location = it })
                ProfileSaveButton(name, location)
            }
        }

    }
}
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp)
                .align(Alignment.Center),
            color = Color(0xFF00BF81)
        )
    }
}

@Composable
fun LoadFailScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Failed to load data. Please try again later.",
            modifier = Modifier
                .align(Alignment.Center),
        )
    }
}

@Composable
fun ProfileAccountHeader(type: String, name: String) {
    var subHeader = if (type == "foodDonor") "Food Donor" else "Food Receiver"
    Text(text = "Welcome $name!",
        style=MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 8.dp)
    )
    Text(text = "Manage Your $subHeader Account",
        style=MaterialTheme.typography.h6,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
fun ProfileUserPfp(imageURI: String) {
    var pfp: String? by remember { mutableStateOf(imageURI) }
    val painter = rememberAsyncImagePainter(
        if (pfp.isNullOrEmpty()) {
            R.drawable.undraw_breakfast_psiw
            // default pfp
        } else {
            pfp
        }
    )

    var storageRef = storage.reference
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        photoUri = uri
        if (photoUri != null) {
            val inputStream = context.contentResolver.openInputStream(photoUri!!)
            val fileName = "${auth.currentUser?.uid}"
            val imageRef = storageRef.child(fileName)
            var uploadTask = imageRef.putStream(inputStream!!)

            uploadTask.addOnFailureListener {
                println("upload failed")
            }.addOnSuccessListener { taskSnapshot ->
                println("upload success")
                pfp = photoUri.toString()
            }
        }
    }

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card (
            shape = CircleShape,
            modifier = Modifier.size(150.dp),

            ){
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                )
        }
        Text(text = "Tap picture to change profile picture", Modifier.padding(top = 8.dp))
    }
}
@Composable
fun ProfileNameField(name: String, onNameChange: (String) -> Unit) {
    OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
}

@Composable
fun ProfileEmailField(email: String, onEmailChange: (String) -> Unit) {
    OutlinedTextField(value = email, onValueChange = onEmailChange, label = {Text("Email")}, modifier = Modifier.fillMaxWidth(), enabled = false)
}

@Composable
fun ProfileLocationField(location: String, onLocationChange: (String) -> Unit) {
    OutlinedTextField(value = location, onValueChange = onLocationChange, label = {Text("Location (Postal Code)")}, modifier = Modifier.fillMaxWidth())
}
@Composable
fun ProfileSaveButton(name: String, location: String) {
    val apiKey = "AIzaSyBikmTl_I4bRyx83Yk1XNBsE8jfj9z8_TU"
    val context = LocalContext.current
    Button(
        onClick = {
            fetchCoordinatesFromAddress(context, location, apiKey, onSuccess = { lat, lng ->
                saveUserProfileWithCoordinates(name, location, lat, lng, context)
            }, onError = { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            })
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF00BF81),
            contentColor = Color.White
        ),
        content = { Text("Save") }
    )
}

fun saveUserProfileWithCoordinates(name: String, address: String, lat: Double, lng: Double, context: Context) {
    Log.d(TAG, "Saving profile with coordinates: Lat = $lat, Lng = $lng")

    val userProfileUpdates = mapOf(
        "name" to name,
        "location" to address,
        "latitude" to lat,
        "longitude" to lng
    )

    auth.currentUser?.let {
        db.collection("users").document(it.uid)
            .update(userProfileUpdates)
            .addOnSuccessListener {
                Toast.makeText(context, "Profile updated successfully.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating profile", e)
                Toast.makeText(context, "Failed to update profile.", Toast.LENGTH_SHORT).show()
            }
    }
}
fun profileSaveDetails(name: String, location: String, context: Context) {
    auth.currentUser?.let { db.collection("users").document(it.uid)
        .update("name", name, "location", location).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    "Saved successfully.",
                    Toast.LENGTH_SHORT,
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Failed to save.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
}

fun searchOffersRedirect(name: String, context: Context) {
    val intent = Intent(context, SearchOffers::class.java)
    context.startActivity(intent, null)
}

@Composable
fun Signout(name: String) {
    val context = LocalContext.current
    ExtendedFloatingActionButton(
        onClick = {signoutAction(name, context)},
        text = {Text("Sign Out")},
        backgroundColor = Color(0xFF00BF81),
        elevation = FloatingActionButtonDefaults.elevation(0.dp),
        contentColor = Color(0xFFFFFFFF),
    )
}

fun signoutAction(name: String, context: Context) {
    val user = FirebaseAuth.getInstance()
    user.signOut()

    val userCheck = FirebaseAuth.getInstance().getCurrentUser();
    if (userCheck == null) {
        Log.d(TAG, "signOut:success")
        // navController.navigate(Screens.Profile.route)
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent, null)

        Toast.makeText(
            context,
            "Signed Out $name Successfully.",
            Toast.LENGTH_SHORT,
        ).show()

    } else {
        // User is not signed out
        Log.w(TAG, "signOut:failure")
        Toast.makeText(
            context,
            "Sign Out failed.",
            Toast.LENGTH_SHORT,
        ).show()
    }
}

// for converting address to lat long
fun fetchCoordinatesFromAddress(context: Context, address: String, apiKey: String, onSuccess: (Double, Double) -> Unit, onError: (String) -> Unit) {
    val urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=Canada $address&key=$apiKey"

    thread {
        try {
            val url = URL(urlString)
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"

                inputStream.bufferedReader().use { reader ->
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    val jsonResponse = JSONObject(response.toString())
                    val status = jsonResponse.getString("status")
                    if (status == "OK") {
                        val results = jsonResponse.getJSONArray("results")
                        val location = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location")
                        val lat = location.getDouble("lat")
                        val lng = location.getDouble("lng")

                        onSuccess(lat, lng)
                    } else {
                    }
                }
            }
        } catch (e: Exception) {
//            Log.e("Geocoding", "Error fetching coordinates", e)
        }
    }
}