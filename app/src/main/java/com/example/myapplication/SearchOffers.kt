package com.example.myapplication
import MapComposable
import android.content.ContentValues
import androidx.compose.runtime.Composable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import calculateDistance
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.io.Serializable

private lateinit var auth: FirebaseAuth
private lateinit var storage: FirebaseStorage
val firestore = FirebaseFirestore.getInstance()

data class Offer(
    var id: String = "",
    val available: Boolean = false,
    val availableTime: Timestamp? = null,
    val claimedBy: String? = null,
    val description: String = "",
    var imageFilePath: String = "",
    val name: String = "",
    val offeredBy: DocumentReference? = null,
    val portionCount: Int = 0,
    var coverFilePath: String = "",
    var distance: Double = 0.0
): Serializable


@Composable
fun SearchOffersScreen(navController: NavController) {
    auth = Firebase.auth
    storage = Firebase.storage
    var user = auth.currentUser
    var storageRef = storage.reference
    val offers = remember { mutableStateListOf<Offer>() }
    var isLoading by remember { mutableStateOf(true) }
    var numOffers by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf("Using Search Bar") }
    var selectedOffer by remember { mutableStateOf<Offer?>(null) }
    var userInfo by remember { mutableStateOf<Map<String, Any>?>(null) }
    val docRef = user?.let { db.collection("users").document(it.uid) }
    var userLat = userInfo?.get("latitude")
    var userLng = userInfo?.get("longitude")
    LaunchedEffect(selectedOffer == null) {
        if (selectedOffer == null) {
            isLoading = true
        }
        var numCompleteCovers = 0
        var numCompleteOffers = 0
        offers.clear()
        val offersCollection = firestore.collection("offers")
        offersCollection.whereEqualTo("available", true).get()
            .addOnSuccessListener { result ->
                docRef?.get()?.addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data}")
                        userInfo = document.data
                        userLat = userInfo?.get("latitude")
                        userLng = userInfo?.get("longitude")
                        numOffers = result.size()
                        println(numOffers)
                        for (document in result) {
                            val offer = document.toObject(Offer::class.java)
                            offer.id = document.id
                            offer.offeredBy?.let {
                                val docRef = db.collection("users").document(it.id)
                                docRef?.get()?.addOnSuccessListener { document ->
                                    if (document != null) {
                                        Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data}")
                                        println(userLat)
                                        println(userLng)
                                        println(document.data?.get("latitude"))
                                        println(document.data?.get("longitude"))
                                        offer.distance = calculateDistance(
                                            userLat as Double,
                                            userLng as Double,
                                            document.data?.get("latitude") as Double,
                                            document.data?.get("longitude") as Double)
                                        println(offer.distance)
                                    } else {
                                        Log.d(ContentValues.TAG, "No such document")
                                    }
                                }?.addOnFailureListener { exception ->
                                    Log.d(ContentValues.TAG, "get failed with ", exception)
                                }
                                storageRef.child(it.id).downloadUrl.addOnSuccessListener { it ->
                                    if (it != null) {
                                        offer.coverFilePath = it.toString()
                                    }
                                    println(it.toString())
                                    numCompleteCovers += 1
                                    if (numCompleteOffers == numOffers && numOffers == numCompleteCovers) {
                                        isLoading = false
                                    }
                                }.addOnFailureListener {
                                    // handle error
                                    numCompleteCovers += 1
                                    if (numCompleteOffers == numOffers && numOffers == numCompleteCovers) {
                                        isLoading = false
                                    }
                                }
                            }
                            if (offer.imageFilePath != "" && offer.imageFilePath != null) {
                                println("we are here!")
                                storageRef.child(offer.imageFilePath).downloadUrl.addOnSuccessListener { it ->
                                    if (it != null) {
                                        offer.imageFilePath = it.toString()
                                    }
                                    println(it.toString())
                                    numCompleteOffers += 1
                                    if (numCompleteOffers == numOffers && numOffers == numCompleteCovers) {
                                        isLoading = false
                                    }
                                }.addOnFailureListener {
                                    // handle error
                                    numCompleteOffers += 1
                                    if (numCompleteOffers == numOffers && numOffers == numCompleteCovers) {
                                        isLoading = false
                                    }
                                }
                            } else {
                                numCompleteOffers += 1
                                if (numCompleteOffers == numOffers && numOffers == numCompleteCovers) {
                                    isLoading = false
                                }
                            }
                            offers.add(offer)
                        }
                    } else {
                        Log.d(ContentValues.TAG, "No such document")
                    }
                }?.addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }
            }
            .addOnFailureListener { exception ->
                // should probably handle error
            }
    }
    if (isLoading) {
        LoadingScreen()
    }
    else {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF6F6F6)) {
            Scaffold(
                bottomBar = {
                    NavBar(navController, "foodReceiver")
                },
            ) { inner ->
                selectedOffer?.let { OfferDetailsScreen(selectedOffer = it, onOfferSelected = { selectedOffer = it }) }

                if (selectedOffer == null) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = inner.calculateBottomPadding())
                    ) {
                        BrowseOffers()
                        CustomToggle(
                            selectedOption = selectedOption,
                            onOptionSelected = { selectedOption = it })
                        if (selectedOption == "Using Maps") {
                            MapComposable(userLat, userLng, offers, onOfferSelected = { selectedOffer = it })
                        }
                        OffersList(
                            offers = offers,
                            onOfferSelected = { selectedOffer = it })
                    }
                }
            }
        }
    }
}

class SearchOffers: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        storage = Firebase.storage
        val user = auth.currentUser
        var storageRef = storage.reference
        setContent {
            val offers = remember { mutableStateListOf<Offer>() }
            var isLoading by remember { mutableStateOf(true) }
            var numOffers by remember { mutableStateOf(0) }
            var numCompleteCovers by remember { mutableStateOf(0) }
            var numCompleteOffers by remember { mutableStateOf(0) }
            val offersCollection = firestore.collection("offers")
            offersCollection.get()
                .addOnSuccessListener { result ->
                    numOffers = result.size()
                    println(numOffers)
                    for (document in result) {
                        val offer = document.toObject(Offer::class.java)
                        offer.offeredBy?.let {
                            storageRef.child(it.id).downloadUrl.addOnSuccessListener { it ->
                                if (it != null) {
                                    offer.coverFilePath = it.toString()
                                }
                                println(it.toString())
                                numCompleteCovers += 1
                            }.addOnFailureListener {
                                // handle error
                                numCompleteCovers += 1
                            }
                        }
                        if (offer.imageFilePath != "") {
                            storageRef.child(offer.imageFilePath).downloadUrl.addOnSuccessListener { it ->
                                if (it != null) {
                                    offer.imageFilePath = it.toString()
                                }
                                println(it.toString())
                                numCompleteOffers += 1
                            }.addOnFailureListener {
                                // handle error
                                numCompleteOffers += 1
                            }
                        }
                        else {
                            numCompleteOffers += 1
                        }
                        offers.add(offer)
                    }
                }
                .addOnFailureListener { exception ->
                    // should probably handle error
                }

            MyApplicationTheme {
                if (numCompleteOffers != numOffers || numCompleteCovers != numOffers) {
                    LoadingScreen()
                }
                else {
//                    searchOffersScreen(offers = offers)
                }
            }
        }
    }
}

@Composable
fun SearchOffersPlaceholder() {
    Text("Placeholder")
}


@Composable
fun BrowseOffers() {
    Text(
        "Browse Offers",
        color = Color(0xFF00BF81),
        fontSize = 40.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp)
    )
}

@Composable
fun CustomToggle(selectedOption: String, onOptionSelected: (String) -> Unit) {


    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { onOptionSelected("Using Search Bar") },
            modifier = Modifier.weight(1f),
            border = if (selectedOption == "Using Search Bar") BorderStroke(1.dp, Color(0xFF00BF81)) else null,
            elevation = null,
            colors = if (selectedOption == "Using Search Bar") ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00BF81)) else ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF6F6F6))
        ) {
            Text(
                text = "Using Search Bar",
                color = if (selectedOption == "Using Search Bar") Color.White else Color(0xFF00BF81)
            )
        }

        OutlinedButton(
            onClick = { onOptionSelected("Using Maps") },
            modifier = Modifier.weight(1f),
            border = if (selectedOption == "Using Maps") BorderStroke(1.dp, Color(0xFF00BF81)) else null,
            elevation = null,
            colors = if (selectedOption == "Using Maps") ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00BF81)) else ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF6F6F6))
        ) {
            Text(
                text = "Using Maps",
                color = if (selectedOption == "Using Maps") Color.White else Color(0xFF00BF81)
            )
        }
    }
}

@Composable
fun OffersList(offers: List<Offer>, onOfferSelected: (Offer?) -> Unit) {
    LazyColumn (
        modifier = Modifier
            .padding(vertical = 16.dp)
            .padding(horizontal = 48.dp)

    ){
        items(offers) { offers ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                val imagePainter = rememberAsyncImagePainter(
                    if (offers.coverFilePath.isNullOrEmpty()) {
                        R.drawable.undraw_breakfast_psiw
                        // default pfp
                    } else {
                        offers.coverFilePath
                    }
                )
                val context = LocalContext.current
                CustomImageButton(
                    imagePainter = imagePainter,
                    leftText = offers.name,
                    rightText = offers.availableTime?.toDate().toString(),
                    onButtonClick = {
                        onOfferSelected(offers)
                    },
                )
            }
        }
    }
}

@Composable
fun CustomImageButton(
    imagePainter: Painter,
    leftText: String,
    rightText: String,
    onButtonClick: () -> Unit

) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.clickable(onClick = onButtonClick),
        elevation = 4.dp,
    ) {
        Column {
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = leftText,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = rightText,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}

