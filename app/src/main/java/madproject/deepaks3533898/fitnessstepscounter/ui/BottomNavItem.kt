package madproject.deepaks3533898.fitnessstepscounter.ui


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BtmScreen(
    val route: String,
    val title: String
) {

    object Home : BtmScreen(
        "home",
        "Home"
    )

    object History : BtmScreen(
        "history",
        "Stats"
    )

    object Goal : BtmScreen(
        "goal",
        "Goal"
    )

    object BMI : BtmScreen(
        "bmi",
        "BMI"
    )

    object Profile : BtmScreen(
        "profile",
        "Profile"
    )

    object StartWalking : BtmScreen(
        "start_walking",
        "Start Walking"
    )

}

data class BottomNavItem(

    val screen: BtmScreen,

    val icon: ImageVector

)

val bottomItems = listOf(

    BottomNavItem(
        BtmScreen.Home,
        Icons.Default.Home
    ),

    BottomNavItem(
        BtmScreen.History,
        Icons.Default.AutoGraph
    ),

    BottomNavItem(
        BtmScreen.Goal,
        Icons.Default.Category
    ),

    BottomNavItem(
        BtmScreen.BMI,
        Icons.Default.HealthAndSafety
    ),

    BottomNavItem(
        BtmScreen.Profile,
        Icons.Default.Person
    )

)