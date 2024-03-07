package com.example.myapplication
import androidx.compose.runtime.Composable
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import coil.compose.*

private lateinit var auth: FirebaseAuth
val currentUser = FirebaseAuth.getInstance().currentUser
val currentUserEmail = currentUser?.email ?: "No Email!"
val currentUserName = currentUser?.displayName ?: "No Username!"
//val currentUserPassword = currentUser?.updatePassword()

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        setContent {
            ProfileScreen()
        }
    }
}

@Composable
fun ProfileScreen() {
    var name by remember { mutableStateOf(currentUserName) }
    var email by remember { mutableStateOf(currentUserEmail) }
    var password by remember { mutableStateOf("") }
    var location by remember {mutableStateOf("")}
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        accountType()
        userPfp()
        nameField(name = name, onNameChange = {name = it})
        emailField(email = email, onEmailChange = {email = it})
        passwordField(password = password, onPasswordChange =  {password = it})
        locationField(location = location, onLocationChange = {location = it})
        saveButton()
    }
}
@Composable
fun accountType() {
    Text(text = "Food Donor Account", style=MaterialTheme.typography.h4, textAlign = TextAlign.Center)
}

@Composable
fun loadImageFromUri(context: Context, uri: Uri): ImageBitmap {
    val inputStream = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    inputStream?.close()
    return bitmap!!.asImageBitmap()
}
@Composable
fun userPfp() {
    val profilePicture = rememberSaveable{ mutableStateOf("") }
    val painter = rememberAsyncImagePainter(
        if (profilePicture.value.isNullOrEmpty()) {
            R.drawable.undraw_breakfast_psiw
            // default pfp
        } else {
            profilePicture.value
        }
    )

    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()) {
            uri -> uri?.let { profilePicture.value = it.toString()}
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
                modifier = Modifier.wrapContentSize().clickable {
                    imagePicker.launch("image/*")
                })
        }
        Text(text = "Tap picture to change profile picture")
    }
}
@Composable
fun nameField(name: String, onNameChange: (String) -> Unit) {
    TextField(value = name, onValueChange = onNameChange, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
}

@Composable
fun emailField(email: String, onEmailChange: (String) -> Unit) {
    TextField(value = email, onValueChange = onEmailChange, label = {Text("Email")}, modifier = Modifier.fillMaxWidth())
}
@Composable
fun passwordField(password: String, onPasswordChange: (String) -> Unit) {
    TextField(value = password, onValueChange = onPasswordChange, label = {Text("Password")}, modifier = Modifier.fillMaxWidth())
}
@Composable
fun locationField(location: String, onLocationChange: (String) -> Unit) {
    TextField(value = location, onValueChange = onLocationChange, label = {Text("Postal Code")}, modifier = Modifier.fillMaxWidth())
}
@Composable
fun saveButton() {
    ExtendedFloatingActionButton(
        onClick = {saveDetails()},
        text = {Text("Save")},
        backgroundColor = Color(0xFF00BF81),
    )
}

fun saveDetails() {

}