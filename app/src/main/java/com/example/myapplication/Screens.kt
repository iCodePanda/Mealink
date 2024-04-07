import com.example.myapplication.R

sealed class Screens(val route : String, val label : String, val icon : Int? = null) {
    object Login : Screens("login", "Login")
    object Home : Screens("home", "Home")
    object Signup : Screens("signup", "Signup")
    object Profile : Screens("profile", "Profile", R.drawable.account_circle)
    object CreateOffers : Screens ("createOffer", "Create Offer", R.drawable.add)
    object MyOffers : Screens ("myOffers", "My Offers")
    object SearchOffers : Screens ("searchOffers", "Search Offers", R.drawable.search)
}