package com.example.myapplication

import android.util.Log
import androidx.compose.runtime.Composable
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.*
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

private lateinit var auth: FirebaseAuth

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF6F6F6)) {
                    Column(
                        modifier = Modifier.padding(top = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SignInScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun SignInScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    logInEmailField(email = email, onEmailChange = { email = it })
    logInPasswordField(password = password, onPasswordChange = { password = it })
    signInButton(email, password)
}

@Composable
fun signInButton(email: String, password: String) {

    val context = LocalContext.current
    ExtendedFloatingActionButton(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        text = { Text("Log In")},
        backgroundColor = Color(0xFF00BF81),
        contentColor = Color(0xFFFFFFFF),
        onClick = { signIn(email,password,context)},
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}

@Composable
fun logInEmailField(email: String, onEmailChange: (String) -> Unit) {
    OutlinedTextField(value = email, onValueChange = onEmailChange, label = { Text("Email") })
}

@Composable
fun logInPasswordField(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(value = password, onValueChange = onPasswordChange, label = { Text("Password") })
}

fun signIn(email: String, password: String, context: Context) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val intent = Intent(context, UserProfileActivity::class.java)
                context.startActivity(intent, null)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(
                    context,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
//                updateUI(null)
            }
        }
}