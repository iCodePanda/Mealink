package com.example.myapplication

import Screens
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.components.Component
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label
import org.checkerframework.common.subtyping.qual.Bottom
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    MyApplicationTheme {
        NavHost(navController = navController, startDestination = Screens.Home.route) {
            composable(Screens.Home.route) {
                Home(navController)
            }
            composable(Screens.Login.route) {
                SignInScreen(navController)
            }
            composable(Screens.Signup.route) {
                SignUpScreen()
            }
            composable(Screens.Profile.route) {
                UserProfileScreen(navController)
            }
        }
    }
}

@Composable
fun Home(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF6F6F6)) {
        Column(
            modifier = Modifier.padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp),
        ) {
            Title()
            MainPageImage()
            ButtonGoogle()
            ButtonEmail { navController.navigate(Screens.Login.route) }
            NotMember()
            ButtonCreateAccount { navController.navigate(Screens.Signup.route) }
        }
    }
}

@Composable
fun ButtonGoogle() {
    val context = LocalContext.current
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        backgroundColor = Color(0xFFFFFFFF),
        text = { Text("Continue with Google") },

        onClick = {  },
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
fun ButtonEmail(navigateTo: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        backgroundColor = Color(0xFF00BF81),
        contentColor = Color(0xFFFFFFFF),
        text = { Text("Login with Email") },
        onClick = { navigateTo() },
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
fun ButtonCreateAccount(navigateTo: () -> Unit) {
    val context = LocalContext.current
    println(context)
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        backgroundColor = Color(0xFF00BF81),
        contentColor = Color(0xFFFFFFFF),
        text = { Text("Create an Account!") },
        onClick = { navigateTo() },
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}

@Composable
fun NotMember() {
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
fun Title() {
    Text(
        "Mealink",
        color = Color(0xFF00BF81),
        fontSize = 65.sp,
    )
}
@Composable
fun MainPageImage() {
    Image(
        painter = painterResource(id = R.drawable.undraw_breakfast_psiw),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()
    )
}
@Composable
fun NavBar(navController: NavController, accountType: String) {
    val items = listOf(
        if (accountType == "foodDonor") {
            Screens.CreateOffers
        } else {
            Screens.SearchOffers
        },
        Screens.Profile,
    )
    BottomNavigation(
        contentColor = Color.Yellow,
        backgroundColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                modifier = Modifier.size(100.dp),
                icon = {
                    if (item.icon != null) {
                        Icon(
                            painter = painterResource(id = item.icon), contentDescription = null
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.undraw_breakfast_psiw), contentDescription = null
                        )
                    }

                },
                label = {
                    Text(
                        text = item.label,
//                    fontSize = 9.sp
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
//                        navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
//                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}