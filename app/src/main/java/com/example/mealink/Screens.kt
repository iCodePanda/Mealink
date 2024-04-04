import com.example.mealink.R

sealed class Screens(val route : String, val label : String, val icon : Int? = null) {
    object Login : Screens("login", "Login")
    object Home : Screens("home", "Home")
    object Signup : Screens("signup", "Signup")
    object Profile : Screens("profile", "Profile", R.drawable.account_circle)
    object CreateOffers : Screens ("createOffer", "Create Offer", R.drawable.add)
    object SearchOffers : Screens ("searchOffers", "Search Offers", R.drawable.search)
}