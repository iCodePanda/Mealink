package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

private lateinit var auth: FirebaseAuth

@Composable
fun SignInScreen(navController: NavController) {
    auth = Firebase.auth
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier.fillMaxSize(), color = Color(0xFFF6F6F6)
    ) {
        Column(
            modifier = Modifier.padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Text(
                "Log In",
                color = Color(0xFF00BF81),
                fontSize = 50.sp,
            )
            SignInEmailField(email = email, onEmailChange = { email = it })
            SignInPasswordField(password = password, onPasswordChange = { password = it })
            SignInButton(email, password, navController)
        }
    }
}

@Composable
fun SignInEmailField(email: String, onEmailChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") })
}

@Composable
fun SignInPasswordField(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun SignInButton(email: String, password: String, navController: NavController) {
    val context = LocalContext.current
    ExtendedFloatingActionButton(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
        text = { Text("Log In") },
        backgroundColor = Color(0xFF00BF81),
        contentColor = Color(0xFFFFFFFF),
        onClick = {
            val emailFormat = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
            if (!email.matches(emailFormat)) {
                Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show()
            } else if (password == "") {
                Toast.makeText(context, "Please enter a password", Toast.LENGTH_SHORT).show()
            } else {
                signIn(email, password, context, navController) }
            },
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}

fun signIn(email: String, password: String, context: Context,navController: NavController) {
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithEmail:success")
            navController.navigate(Screens.Profile.route)
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithEmail:failure", task.exception)
            Toast.makeText(
                context,
                "Authentication failed.",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}