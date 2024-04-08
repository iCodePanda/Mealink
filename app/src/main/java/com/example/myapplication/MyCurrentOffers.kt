package com.example.myapplication
import android.content.ContentValues
import android.content.Context
import androidx.compose.runtime.Composable
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

private lateinit var auth: FirebaseAuth
private lateinit var storage: FirebaseStorage

@Composable
fun MyOffers(navController: NavController) {
    auth = Firebase.auth
    storage = Firebase.storage
    val user = auth.currentUser
    var storageRef = storage.reference
    val offers = remember { mutableStateListOf<Offer>() }
    var isLoading by remember { mutableStateOf(true) }
    var numOffers by remember { mutableStateOf(0) }
    var selectedOffer by remember { mutableStateOf<Offer?>(null) }
    var userInfo by remember { mutableStateOf<Map<String, Any>?>(null) }
    val docRef = user?.let { db.collection("users").document(it.uid) }
    var counter by remember { mutableStateOf(0) }

    LaunchedEffect(counter) {
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
                        numOffers = result.size()
                        println(numOffers)
                        for (document in result) {
                            val offer = document.toObject(Offer::class.java)
                            offer.id = document.id
                            offer.offeredBy?.let {
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
        Scaffold(
            bottomBar = {
                NavBar(navController, "foodDonor")
            },
            topBar = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                    Row {
                        ExtendedFloatingActionButton(
                            onClick = {
                                navController.navigate(Screens.Profile.route)
                            },
                            text = { Text("Back") },
                            backgroundColor = Color(0xFF00BF81),
                            elevation = FloatingActionButtonDefaults.elevation(0.dp),
                            contentColor = Color(0xFFFFFFFF),
                        )
                        Spacer(Modifier.weight(1f))

                        ExtendedFloatingActionButton(
                            onClick = { counter++ },
                            backgroundColor = Color(0xFF00BF81),
                            contentColor = Color(0xFFFFFFFF),
                            text = {Text("Refresh")}
                        )
                    }
                    Text(text = "Manage Current Offers",
                        style=MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        ) { inner ->
            Surface (modifier = Modifier.padding(inner)) {
                MyOffersList(
                    offers = offers.filter { it.offeredBy?.id == user?.uid }
                )
            }
        }
    }
}

@Composable
fun MyOffersList(offers: List<Offer>) {
    LazyColumn (
        modifier = Modifier
            .padding(vertical = 16.dp)
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(offers) { offers ->
            OfferRow(
                leftText = offers.name,
                rightText = offers.availableTime?.toDate().toString(),
                id = offers.id,
                name = offers.name,
            )
        }
    }
}

@Composable
fun OfferRow(
    leftText: String,
    rightText: String,
    id: String,
    name: String
) {
    val context = LocalContext.current
    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(24.dp)),
    ) {
        Column () {
            ExtendedFloatingActionButton(
                onClick = { deleteOffer(id, name, context) },
                text = {Text("X")},
                backgroundColor = Color.Red,
                contentColor = Color(0xFFFFFFFF),
                modifier = Modifier.align(Alignment.End).padding(8.dp),
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
                    modifier = Modifier.weight(1f).padding(8.dp),
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

fun deleteOffer(offerId: String, name: String, context: Context) {
    db.collection("offers").document(offerId)
        .update("available", false)
        .addOnSuccessListener {
            println("set to false")
            Toast.makeText(
                context,
                "Deleted ${name}. Please refresh to see changes.",
                Toast.LENGTH_SHORT,
            ).show()
        }
        .addOnFailureListener{
            println("unlucky")
        }
}