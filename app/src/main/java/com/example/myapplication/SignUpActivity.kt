package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


private lateinit var auth: FirebaseAuth
val db = Firebase.firestore

@Composable
fun SignUpScreen() {
    auth = Firebase.auth
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    MyApplicationTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF6F6F6)) {
            Column(
                modifier = Modifier.padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                SubTitle()
                EmailField(email = email, onEmailChange = { email = it })
                PasswordField(password = password, onPasswordChange = { password = it })
                NameField(name = name, onNameChange = { name = it })
                TypeField(type = type, onTypeChange = { type = it })
                LocationField(location = location, onLocationChange = { location = it })
                SignUpButton(email, password, name, type, location)
            }
        }
    }
}

@Composable
fun SignUpButton(email: String, password: String, name: String, type: String, location: String) {
    val context = LocalContext.current
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        backgroundColor = Color(0xFF00BF81),
        contentColor = Color(0xFFFFFFFF),
        text = { Text("Sign Up") },
        onClick = {
            val emailFormat = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
            val passwordFormat = Regex("^(?=.*[0-9]).{8,}\$")
            val locationFormat = Regex("^[A-Z]\\d[A-Z] \\d[A-Z]\\d\$")
            if (!email.matches(emailFormat)) {
                Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show()
            }
            else if (!password.matches(passwordFormat)) {
                Toast.makeText(context, "Invalid Password", Toast.LENGTH_SHORT).show()
            }
            else if (name == "") {
                Toast.makeText(context, "Please Enter a Name", Toast.LENGTH_SHORT).show()
            }
            else if (type == "") {
                Toast.makeText(context, "Please Select a Type", Toast.LENGTH_SHORT).show()
            }
            else if (!location.matches(locationFormat)) {
                Toast.makeText(context, "Invalid Postal Code", Toast.LENGTH_SHORT).show()
            }
            else {
                createAccount(email, password, name, type, location, context)
            }
                  },
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}

@Composable
fun NameField(name: String, onNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Name") }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TypeField(type: String, onTypeChange: (String) -> Unit) {
    val options = arrayOf("foodDonor", "foodReceiver")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Select an Option") }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            OutlinedTextField(
                value = type,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                label = { Text("Type") }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { item ->
                    DropdownMenuItem(
                        content = { Text(text = item) },
                        onClick = {
                            expanded = false
                            onTypeChange(item)
                        }
                    )
                }
            }
        }
}

@Composable
fun LocationField(location: String, onLocationChange: (String) -> Unit) {
    OutlinedTextField(
        value = location,
        onValueChange = onLocationChange,
        label = { Text("Location (Postal Code)") }
    )
}

@Composable
fun EmailField(email: String, onEmailChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") }
    )
}

@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        )
}


fun createAccount(email: String, password: String, name: String, type: String, location: String, context: Context) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                fetchCoordinatesFromAddress(context, location, "AIzaSyBikmTl_I4bRyx83Yk1XNBsE8jfj9z8_TU", onSuccess = { lat, lng ->
                    val userTableEntry = hashMapOf(
                        "name" to name,
                        "location" to location,
                        "type" to type,
                        "email" to email,
                        "latitude" to lat,
                        "longitude" to lng
                    )
                    db.collection("users")
                        .document(user!!.uid)
                        .set(userTableEntry)
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot added")
                            Toast.makeText(
                                context,
                                "User $name Registered Successfully!",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                }, onError = { errorMessage ->
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                })
//                    updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(
                    context,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
//                    updateUI(null)
            }
        }
}

@Composable
fun SubTitle() {
    Text(
        "Welcome to Mealink!",
        color = Color(0xFF00BF81),
        fontSize = 30.sp,
    )
    Text(
        text = "Create an account below to get started.",
        color = Color(0xFF000000),
        fontSize = 15.sp
    )
}