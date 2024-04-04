package com.example.mealink

import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mealink.ui.theme.MyApplicationTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage


private lateinit var auth: FirebaseAuth
private lateinit var storage: FirebaseStorage

class ProfilePicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        storage = Firebase.storage

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF6F6F6)) {
                    Column(
                        modifier = Modifier.padding(top = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        uploadButton()
                    }
                }
            }
        }
    }
}

@Composable
fun uploadButton() {
    var storageRef = storage.reference
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        photoUri = uri
        val inputStream = context.contentResolver.openInputStream(photoUri!!)
        val fileName = "${System.currentTimeMillis()}"
        val imageRef = storageRef.child(fileName)
        var uploadTask = imageRef.putStream(inputStream!!)

        uploadTask.addOnFailureListener {
            println("upload failed")
        }.addOnSuccessListener { taskSnapshot ->
            println("upload success")
        }
    }

    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = { Text("Upload")},
        backgroundColor = Color(0xFF00BF81),
        contentColor = Color(0xFFFFFFFF),
        onClick = {
            launcher.launch(
                PickVisualMediaRequest(
                    PickVisualMedia.ImageOnly
                )
            )
        },
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}