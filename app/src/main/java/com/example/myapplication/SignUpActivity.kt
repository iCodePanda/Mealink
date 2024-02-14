package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


private lateinit var auth: FirebaseAuth

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
                        SubTitle()
                        Spacer(modifier = Modifier.padding(top = 35.dp))
                        SignUpScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun SignUpScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    EmailField(email = email, onEmailChange = { email = it })
    Spacer(modifier = Modifier.padding(top = 20.dp))
    PasswordField(password = password, onPasswordChange = { password = it })
    Spacer(modifier = Modifier.padding(top = 20.dp))
    SignUpButton(email, password)
}

@Composable
fun SignUpButton(email: String, password: String) {
    val context = LocalContext.current
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        backgroundColor = Color(0xFF00BF81),
        contentColor = Color(0xFFFFFFFF),
        text = { Text("Sign Up") },
        onClick = { createAccount(email, password, context) },
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}

@Composable
fun AccountNameField(accountName: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = accountName,
        onValueChange = onPasswordChange,
        label = { Text("Name") }
    )
}
@Composable
fun EmailField(email: String, onEmailChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Name") }
    )
}

@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Name") }
    )
}


fun createAccount(email: String, password: String, context: Context) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser
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