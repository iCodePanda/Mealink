package com.example.myapplication
import android.accounts.Account
import android.content.ContentValues.TAG
import androidx.compose.runtime.Composable
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.layout.ContentScale
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import coil.compose.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

private lateinit var auth: FirebaseAuth
private lateinit var storage: FirebaseStorage
class SearchOffers: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                SearchOffersScreen()
            }
        }
    }
}

@Composable
fun SearchOffersScreen() {
    var selectedOption by remember { mutableStateOf("Using Search Bar") }
    Column {
        BrowseOffers()
        CustomToggle()
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
fun CustomToggle() {

    var selectedOption by remember { mutableStateOf("Using Search Bar") }
    Row(modifier = Modifier.fillMaxWidth()) {
        // Personal Account Button
        OutlinedButton(
            onClick = { selectedOption = "Using Search Bar" },
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
            onClick = { selectedOption = "Using Maps" },
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
