sealed class Screens(val route : String) {
    object Login : Screens("login")
    object Home : Screens("home")
    object Signup : Screens("signup")
    object Profile : Screens("profile")
}