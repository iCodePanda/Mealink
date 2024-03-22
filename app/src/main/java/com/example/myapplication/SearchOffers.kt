package com.example.myapplication
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.material.search.SearchBar
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.Serializable

private lateinit var auth: FirebaseAuth
private lateinit var storage: FirebaseStorage
val firestore = FirebaseFirestore.getInstance()

data class Offer(
    val available: Boolean = false,
    val availableTime: Timestamp? = null,
    val claimedBy: String? = null,
    val description: String = "",
    val imageFilePath: String = "",
    val name: String = "",
    //val offeredBy: String = "",
    val portionCount: Int = 0
): Serializable

class SearchOffers: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        storage = Firebase.storage
        setContent {
            val offers = remember { mutableStateListOf<Offer>() }
            val offersCollection = firestore.collection("offers")
            offersCollection.get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val offer = document.toObject(Offer::class.java)
                        offers.add(offer)
                    }
                }
                .addOnFailureListener { exception ->
                    // should probably handle error
                }
            MyApplicationTheme {
                searchOffersScreen(offers = offers)
            }
        }
    }
}

@Composable
fun searchOffersScreen(offers: List<Offer>) {
    var selectedOption by remember { mutableStateOf("Using Search Bar") }

    Column {
        BrowseOffers()
        CustomToggle(selectedOption = selectedOption, onOptionSelected = { selectedOption = it })
        if (selectedOption == "Using Maps") {
            MapComposable()
        }
        OffersList(offers = offers)
    }


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
        // Personal Account Button
        OutlinedButton(
            onClick = { onOptionSelected("Using Search Bar") },
            modifier = Modifier.weight(1f),
            // Remove gap by setting border to null and elevation to 0.dp
            border = if (selectedOption == "Using Search Bar") BorderStroke(1.dp, Color(0xFF00BF81)) else null,
            elevation = null,
            colors = if (selectedOption == "Using Search Bar") ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00BF81)) else ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF6F6F6))
        ) {
            Text(
                text = "Using Search Bar",
                color = if (selectedOption == "Using Search Bar") Color.White else Color(0xFF00BF81)
            )
        }

        // Business Account Button
        OutlinedButton(
            onClick = { onOptionSelected("Using Maps") },
            modifier = Modifier.weight(1f),
            // Remove gap by setting border to null and elevation to 0.dp
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
fun OffersList(offers: List<Offer>) {
    LazyColumn (
        modifier = Modifier
            .padding(vertical = 16.dp)
            .padding(horizontal = 48.dp)

    ){
        items(offers) { offers ->
            Box(
                modifier = Modifier
                    .padding(8.dp) // Add padding around each item for spacing
                    .fillMaxWidth()
            ) {
                val imagePainter = painterResource(id = R.drawable.tims)
                val context = LocalContext.current
                CustomImageButton(
                    imagePainter = imagePainter,
                    leftText = offers.name,
                    rightText = offers.availableTime?.toDate().toString(),
                    onButtonClick = {
                        val intent = Intent(context, OfferDetailActivity::class.java).apply {
                            putExtra("offerName", offers.name)
                            putExtra("offerDescription", offers.description)
                            putExtra("offerImageFilePath", offers.imageFilePath)
                            putExtra("offerPortionCount", offers.portionCount)
                        }
                        context.startActivity(intent)
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
        shape = RoundedCornerShape(24.dp), // corners rounded
        modifier = Modifier.clickable(onClick = onButtonClick),
        elevation = 4.dp,
    ) {
        Column {
            // Image part
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
