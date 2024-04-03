package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


//class OfferDetailActivity : AppCompatActivity() {
//    val db = FirebaseFirestore.getInstance()
//    val auth : FirebaseAuth = Firebase.auth
//    val userUid = auth.currentUser?.uid
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_offer_detail)
//        val offerName = intent.getStringExtra("offerName")
//        val offerDescription = intent.getStringExtra("offerDescription")
//        val offerImageFilePath = intent.getStringExtra("offerImageFilePath")
//        val offerPortionCount = intent.getIntExtra("offerPortionCount", -1)
//        val offeredByUid = intent.getStringExtra("offeredByUID")
//        val offerAvailableTime = intent.getStringExtra("availableTime")
//        val offerId = intent.getStringExtra("offerId")
//
//        setContent {
//            MyApplicationTheme {
//                Column (modifier = Modifier
//                        .fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally){
//                    OfferDetails()
//                    if (offerName != null) {
//                        offerText(offerName)
//                    }
//                    if (offerDescription != null) {
//                        offerText(offerDescription)
//                    }
//                    if (offerPortionCount != -1) {
//                        portionText(offerPortionCount.toString())
//                    }
//                    if (offerImageFilePath != null) {
//                        offerPic(offerImageFilePath)
//                    }
//                    AcceptOfferButton(
//                        offerName!!,
//                        offerDescription!!,
//                        offerPortionCount.toString(),
//                        offeredByUid!!,
//                        userUid!!,
//                        offerAvailableTime!!,
//                        offerId!!,
//                        onOfferSelected
//                    )
//                }
//            }
//        }
//
//    }
//}

@Composable
fun OfferDetailsScreen(
    selectedOffer : Offer,
    onOfferSelected : (Offer?) -> Unit
) {
    val auth : FirebaseAuth = Firebase.auth
    Column (modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        OfferDetails(onOfferSelected)
        if (selectedOffer.name != null) {
            offerText(selectedOffer.name)
        }
        if (selectedOffer.description != null) {
            offerText(selectedOffer.description)
        }
        if (selectedOffer.portionCount != -1) {
            portionText(selectedOffer.portionCount.toString())
        }
        if (selectedOffer.imageFilePath != null) {
            offerPic(selectedOffer.imageFilePath)
        }
        AcceptOfferButton(selectedOffer.name!!,
            selectedOffer.description!!,
            selectedOffer.portionCount.toString(),
            selectedOffer.offeredBy?.id!!,
            auth.currentUser?.uid!!,
            selectedOffer.availableTime?.toDate().toString()!!,
            selectedOffer.id!!,
            onOfferSelected)
    }
}

@Composable
fun OfferDetails(onOfferSelected: (Offer?) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            IconButton(
                onClick = { onOfferSelected(null) },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Arrow",
                    tint = Color(0xFF00BF81)
                )
            }
            Text(
                "Offer Details",
                color = Color(0xFF00BF81),
                fontSize = 40.sp,
                modifier = Modifier
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}

@Composable
fun offerText(text : String) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = 20.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp)
    )
}

@Composable
fun portionText(text : String) {
    Text(
        text = "Portions: $text",
        color = Color.Black,
        fontSize = 20.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp)
    )
}

@Composable
fun offerPic(uri: String) {
    val painter = rememberAsyncImagePainter(
        if (uri.isNullOrEmpty()) {
            R.drawable.undraw_breakfast_psiw
            // default pfp
        } else {
            uri
        }
    )
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .wrapContentSize()
            .height(300.dp)
    )
}

@Composable
fun AcceptOfferButton(
    offerName: String,
    offerDescription: String,
    offerPortionCount: String,
    offeredByUid: String,
    userUid: String,
    offerAvailableTime: String,
    offerId: String,
    onOfferSelected: (Offer?) -> Unit
)
{
    var isLoading by remember { mutableStateOf(false) }
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        backgroundColor = Color(0xFF00BF81),
        contentColor = Color(0xFFFFFFFF),
        text = {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("Accept Offer")
            }
        },
        onClick = {
            isLoading = true
            val mailTableEntry = hashMapOf(
                "toUids" to listOf(offeredByUid, userUid),
                "message" to hashMapOf(
                    "subject" to "Offer Acceptance Confirmation: $offerName",
                    "text" to "Offer Name: $offerName\n" +
                            "Offer Description: $offerDescription\n" +
                            "Portions: $offerPortionCount\n" +
                            "Pickup Time: $offerAvailableTime"
                )
            )
            db.collection("mail")
                .add(mailTableEntry)
                .addOnSuccessListener {
                    println("done")
                }
                .addOnFailureListener {
                    println("nope")
                }
            db.collection("offers").document(offerId)
                .update("available", false)
                .addOnSuccessListener {
                    println("set to false")

                    onOfferSelected(null)
                }
                .addOnFailureListener{
                    println("unlucky")
                }
        }
    )
}