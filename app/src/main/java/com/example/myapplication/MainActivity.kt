package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF4F4F4)) {

                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    title()
                    button("Sign in with Google")
                    button("Log in with Email")
                    Text("Don't have an account?")

                    button("Create Account")
                }

                //test("Click me!")

            }
        }
    }
}


@Composable
fun button(name: String) {
//    Column(modifier = Modifier.fillMaxSize(),
//        horizontal)
    Button(onClick = { println("Button pressed") }) {Text("$name")}
}

@Composable
fun title(){
    Text(
        "Mealink",
        color = Color(0xFF00BF81),
        fontSize = 65.sp,
    )
}

//@Composable
//fun checkbox() {
//    val isChecked = remember {mutableStateOf(false)}
//    Checkbox(
//        checked = isChecked.value,
//        enabled = true,
//        onCheckedChange = {
//            isChecked.value = it
//        }
//}

//@Composable
//fun mainPageImage() {
//    Image(
//        painter = painterResource(undraw_breakfast_psiw.png)
//        contentDescription = null,
//        contentScale = ContentScale.Fit,
//        modifier = Modifier
//            .height(150.dp)
//            .fillMaxWidth()
//            .clip(shape = RoundedCornerShape(10.dp))
//    )
//}
