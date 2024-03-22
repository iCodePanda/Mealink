package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                        .fillMaxSize(), // Fills the parent size to ensure the Column can be centered
                        horizontalAlignment = Alignment.CenterHorizontally){
                    OfferDetails()
                    if (offerName != null) {
                        offerText(offerName)
                    if (offerDescription != null) {
                        offerText(offerDescription)
                    }
                    }
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
