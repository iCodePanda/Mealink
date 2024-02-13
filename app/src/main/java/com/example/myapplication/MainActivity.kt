package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.core.content.ContextCompat.startActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF6F6F6)) {
                    Column(
                        modifier = Modifier.padding(top = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        title()
                        mainPageImage()
                        //Spacer(modifier = Modifier.padding(top = 30.dp))
                        buttonGoogle()
                        Spacer(modifier = Modifier.padding(top = 25.dp))
                        buttonEmail()
                        Spacer(modifier = Modifier.padding(top = 25.dp))
                        Box(modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp),
                            contentAlignment = Alignment.CenterStart) {
                            notMember()
                        }
                        buttonCreateAccount()
                    }
                }
            }
        }
    }
}

@Composable
fun buttonGoogle() {
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        backgroundColor = Color(0xFFFFFFFF),
        text = { Text("Continue with Google") },
        onClick = { /*idk*/ },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.google),
                "",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
        },
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}

@Composable
fun buttonEmail() {
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        backgroundColor = Color(0xFF00BF81),
        contentColor = Color(0xFFFFFFFF),
        text = { Text("Login with Email") },
        onClick = { /*idk*/ },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.email),
                "",
                tint = Color.Unspecified,
                modifier = Modifier.size(25.dp)
            )
        },
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}

@Composable
fun buttonCreateAccount() {
    val context = LocalContext.current
    println(context)
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        backgroundColor = Color(0xFF00BF81),
        contentColor = Color(0xFFFFFFFF),
        text = { Text("Create an Account!") },
        onClick = { startActivity(context, Intent(context, SignUpActivity::class.java), null) },
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}

@Composable
fun notMember() {
    Text(
        "Not a member yet?",
        style = TextStyle(
            shadow = Shadow(
                color = Color.LightGray,
                offset = Offset(4f, 4f),
                blurRadius = 8f
            )
        )
    )
}
@Composable
fun title() {
    Text(
        "Mealink",
        color = Color(0xFF00BF81),
        fontSize = 65.sp,
    )
}
@Composable
fun mainPageImage() {
    Image(
        painter = painterResource(id = R.drawable.undraw_breakfast_psiw),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    )
}


