sealed class Screens(val route : String, val label : String) {
    object Login : Screens("login", "Login")
    object Home : Screens("home", "Home")
    object Signup : Screens("signup", "Signup")
    object Profile : Screens("profile", "Profile")
}