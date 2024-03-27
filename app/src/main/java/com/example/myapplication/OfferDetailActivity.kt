package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.api.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage



class OfferDetailActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_detail)
        val offerName = intent.getStringExtra("offerName")
        val offerDescription = intent.getStringExtra("offerDescription")
        val offerImageFilePath = intent.getStringExtra("offerImageFilePath")
        val offerPortionCount = intent.getIntExtra("offerPortionCount", -1)

        setContent {
            MyApplicationTheme {
                Column (modifier = Modifier
                        .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally){
                    OfferDetails()
                    if (offerName != null) {
                        offerText(offerName)
                        if (offerDescription != null) {
                            offerText(offerDescription)
                        }
                    }
                    if (offerImageFilePath != null) {
                        offerPic(offerImageFilePath)
                    }
                    AcceptOfferButton()
                }
            }
        }

    }
}

@Composable
fun OfferDetails() {
    Text(
        "Offer Details",
        color = Color(0xFF00BF81),
        fontSize = 40.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp)
    )
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
fun AcceptOfferButton()
{
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        backgroundColor = Color(0xFF00BF81),
        contentColor = Color(0xFFFFFFFF),
        text = { Text("Accept Offer") },
        onClick = {
            println("accepted")
        }
    )
}