package com.example.mealink
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.*
import com.example.mealink.ui.theme.MyApplicationTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.text.SimpleDateFormat
import com.google.firebase.firestore.firestore
import java.io.File
import java.util.Date
import kotlin.io.path.createTempFile
import kotlin.io.path.outputStream

private lateinit var auth: FirebaseAuth
private lateinit var storage: FirebaseStorage

@Composable
fun OfferCreateScreen(navController: NavController) {
    storage = Firebase.storage
    auth = Firebase.auth
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var portionCount by remember { mutableStateOf("") }
    var selectedDate by remember { mutableLongStateOf(0L) }
    var selectedTime by remember { mutableLongStateOf(0L) }
    var selectedImageFile by remember { mutableStateOf("") }

    MyApplicationTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF6F6F6)) {
            Scaffold(
                bottomBar = {
                    NavBar(navController, "foodDonor")
                },
            ) { inner ->
                Column(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .padding(bottom = inner.calculateBottomPadding()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Heading()
                    ItemName(name, onNameChange = { name = it })
                    Description(description, onDescChange = { description = it })
                    PortionCount(portionCount, onCountChange = { portionCount = it })
                    DatePicker(onDateChange = {
                        selectedDate = it
                    })
                    TimePicker(onTimeChange = {
                        selectedTime = it
                    })
                    ItemPicture(imageURI = "", onImageSelected = { selectedImageFile = it })
                    AddOfferButton(
                        name,
                        description,
                        portionCount,
                        selectedDate + selectedTime + 14400000L,
                        selectedImageFile
                    )
                }
            }
        }
    }
}

@Composable
fun Heading() {
    Text(
        "Create Food Listing",
        color = Color(0xFF00BF81),
        fontSize = 30.sp
    )
}

@Composable
fun ItemName(name: String, onNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Item Name") }
    )
}

@Composable
fun Description(description: String, onDescChange: (String) -> Unit) {
    OutlinedTextField(
        value = description,
        onValueChange = onDescChange,
        label = { Text("Description") }
    )
}

@Composable
fun PortionCount(portionCount: String, onCountChange: (String) -> Unit) {
    OutlinedTextField(
        value = portionCount,
        onValueChange = onCountChange,
        label = { Text("Portion Count") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(onDateChange: (Long) -> Unit) {
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    var tz = java.util.TimeZone.getTimeZone("GMT")
    formatter.timeZone = tz

    OutlinedTextField(
        value = if (datePickerState.selectedDateMillis != null) {
            formatter.format(datePickerState.selectedDateMillis)
        }
        else {
             ""
             },
        onValueChange = { println("changed date") },
        label = { Text("Available Date", color = Color.DarkGray) },
        enabled = false,
        modifier = Modifier.clickable { showDatePicker = true },
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { println("window closed") },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { onDateChange(it) }
                        showDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) { Text("Cancel") }
            }
        )
        {
            DatePicker(state = datePickerState)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(onTimeChange: (Long) -> Unit) {
    val timePickerState = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = (timePickerState.hour.toString() + ":" + timePickerState.minute.toString()),
        onValueChange = { println("time changed") },
        label = { Text("Available Time", color = Color.DarkGray) },
        enabled = false,
        modifier = Modifier.clickable { showTimePicker = true },
    )

    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { println("window closed") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                        onTimeChange(timePickerState.hour * 3600000L + timePickerState.minute * 60000L)
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("Cancel") }
            }
        )
        {
            TimePicker(state = timePickerState)
        }
    }
}

// got this time picker dialog from here: https://medium.com/@droidvikas/exploring-date-and-time-pickers-compose-bytes-120e75349797
@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(modifier = Modifier.width(IntrinsicSize.Min).height(IntrinsicSize.Min)) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp), text = title)
                content()
                Row(modifier = Modifier.height(40.dp).fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}

@Composable
fun ItemPicture(imageURI: String, onImageSelected: (String) -> Unit) {
    var pfp: String? by remember { mutableStateOf(imageURI) }
    val painter = rememberAsyncImagePainter(
        if (pfp.isNullOrEmpty()) {
            R.drawable.undraw_breakfast_psiw
            // default pfp
        } else {
            pfp
        }
    )

    var photoUri: Uri? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        photoUri = uri
        if (photoUri != null) {
            val inputStream = context.contentResolver.openInputStream(photoUri!!)
            pfp = photoUri.toString()
            val tempFile = createTempFile(suffix = ".jpg")
            inputStream?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            onImageSelected(tempFile.toString())
        }
    }

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
            )
        }
        Text(text = "Tap to upload image", Modifier.padding(top = 8.dp))
    }
}
@Composable
fun CreateOffersPlaceholder() {
    Text("Placeholder")
}

@Composable
fun AddOfferButton(
    name: String,
    description: String,
    portionCount: String,
    availableTime: Long,
    selectedImageFile: String
) {
    val context = LocalContext.current
    val db = Firebase.firestore
    var storageRef = storage.reference

    ExtendedFloatingActionButton(
        onClick = {
            if (name == "") {
                Toast.makeText(context, "Please Enter a Name", Toast.LENGTH_SHORT).show()
            }
            else if (description == "") {
                Toast.makeText(context, "Please Enter a Description", Toast.LENGTH_SHORT).show()
            }
            else if (portionCount.toIntOrNull() == null) {
                Toast.makeText(context, "Please Enter a Valid Portion Count", Toast.LENGTH_SHORT).show()
            }
            else if (availableTime < System.currentTimeMillis()) {
                Toast.makeText(context, "Please Enter a Valid Time", Toast.LENGTH_SHORT).show()
            }
            else if (selectedImageFile == "") {
                Toast.makeText(context, "Please Select an Image", Toast.LENGTH_SHORT).show()
            }
            else {
                val user = auth.currentUser
                val offerTableEntry = hashMapOf(
                    "available" to true,
                    "availableTime" to Date(availableTime),
                    "claimedBy" to null,
                    "description" to description,
                    "imageFilePath" to "",
                    "name" to name,
                    "offeredBy" to db.document("users/" + (user?.uid ?: "")),
                    "portionCount" to portionCount.toInt()
                )
                selectedImageFile?.let { path ->
                    val file = File(path)
                    val fileName = "${System.currentTimeMillis()}.jpg"
                    val imageRef = storageRef.child(fileName)

                    val uri = Uri.fromFile(file)
                    val uploadTask = imageRef.putFile(uri)

                    uploadTask.addOnSuccessListener {
                        offerTableEntry["imageFilePath"] = fileName
                        db.collection("offers")
                            .add(offerTableEntry)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                                val intent = Intent(context, UserProfileActivity::class.java)
                                context.startActivity(intent, null)
                                Toast.makeText(context, "Offer Created!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                                Toast.makeText(context, "Failed to create offer", Toast.LENGTH_SHORT).show()
                            }
                    }.addOnFailureListener { exception ->
                        Log.e(TAG, "Error uploading image", exception)
                        Toast.makeText(context, "Failed to create offer", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        },
        text = {Text("Create Offer")},
        backgroundColor = Color(0xFF00BF81),
        elevation = FloatingActionButtonDefaults.elevation(0.dp),
        contentColor = Color(0xFFFFFFFF),
    )
}