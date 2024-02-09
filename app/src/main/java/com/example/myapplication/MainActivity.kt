package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


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
                        Spacer(modifier = Modifier.padding(top = 30.dp))
                        button("Continue with Google")
                        Spacer(modifier = Modifier.padding(top = 25.dp))
                        button("Log in with Email")
                        Spacer(modifier = Modifier.padding(top = 25.dp))
                        button("Create New Account")
                    }
                }
            }
        }
    }
}


@Composable
fun button(name: String) {
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = { Text("$name") },
        onClick = { /*idk*/ },
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
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

@Composable
fun InnerShadowText(text: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawIntoCanvas { canvas ->
                // Save the canvas state
                val saved = canvas.nativeCanvas.save()

                // Apply the text style
                val paint = androidx.compose.ui.graphics.Paint().asFrameworkPaint()
                paint.textSize = 48f // Text size
                paint.color = Color.Black.toArgb() // Text color
                paint.textAlign = android.graphics.Paint.Align.LEFT

                // Draw the text with the shadow
                val textX = size.width / 2 - paint.measureText(text) / 2
                val textY = size.height / 2
                canvas.nativeCanvas.drawText(text, textX, textY, paint)

                // Apply inner shadow effect
                paint.setShadowLayer(
                    8f, // Shadow radius
                    0f, // Shadow offset X
                    0f, // Shadow offset Y
                    Color.Gray.toArgb() // Shadow color
                )

                // Draw the text again to apply the inner shadow
                canvas.nativeCanvas.drawText(text, textX, textY, paint)

                // Restore the canvas state
                canvas.nativeCanvas.restoreToCount(saved)
            }
        }
    }
}