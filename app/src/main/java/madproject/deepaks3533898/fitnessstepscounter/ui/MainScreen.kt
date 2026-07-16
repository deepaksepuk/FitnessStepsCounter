package madproject.deepaks3533898.fitnessstepscounter.ui


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import madproject.deepaks3533898.fitnessstepscounter.DashboardScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainNavController: NavHostController) {

    val navController = rememberNavController()

    val backStack by navController.currentBackStackEntryAsState()

    val currentRoute = backStack?.destination?.route

    Scaffold(

        bottomBar = {

            PremiumBottomNavigation(

                navController = navController,

                currentRoute = currentRoute,

                items = bottomItems

            )

        }

    ) { padding ->

        NavHost(
            modifier = Modifier.padding(padding),

            navController = navController,

            startDestination = BtmScreen.Home.route

        ) {

            composable(BtmScreen.Home.route) {

                DashboardScreen(mainNavController)

            }

//            composable(BtmScreen.StartWalking.route) {
//
//                StartWalkingScreen(navController)
//
//            }

            composable(BtmScreen.History.route) {


                StatisticsScreen()

            }

            composable(BtmScreen.Goal.route) {


                GoalSettingScreen()

            }

            composable(BtmScreen.BMI.route) {

                BMIScreen()

            }

            composable(BtmScreen.Profile.route) {

                ProfileScreen(mainNavController)

            }

        }

    }

}

@Composable
fun PremiumBottomNavigation(

    navController: NavHostController,

    currentRoute: String?,

    items: List<BottomNavItem>

) {

    Surface(

        shadowElevation = 18.dp,

        color = Color(0xFF17171C),

        shape = RoundedCornerShape(

            topStart = 0.dp,

            topEnd = 0.dp

        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .height(82.dp),

            horizontalArrangement = Arrangement.SpaceEvenly,

            verticalAlignment = Alignment.CenterVertically

        ) {

            items.forEach { item ->

                PremiumNavItem(

                    item = item,

                    selected =

                        currentRoute == item.screen.route,

                    onClick = {

                        navController.navigate(item.screen.route) {

                            popUpTo(

                                navController.graph.findStartDestination().id

                            )

                            launchSingleTop = true

                            restoreState = true

                        }

                    }

                )

            }

        }

    }

}

@Composable
fun PremiumNavItem(

    item: BottomNavItem,

    selected: Boolean,

    onClick: () -> Unit

) {

    val background by animateColorAsState(

        targetValue =

            if (selected)

                Color(0xFF6A00FF)

            else

                Color.Transparent,

        label = ""

    )

    val iconColor by animateColorAsState(

        targetValue =

            if (selected)

                Color.White

            else

                Color.LightGray,

        label = ""

    )

    Column(

        modifier = Modifier
            .clickable {

                onClick()

            }
            .padding(horizontal = 6.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Box(

            modifier = Modifier

                .size(

                    if (selected)

                        52.dp

                    else

                        42.dp

                )

                .clip(CircleShape)

                .background(background),

            contentAlignment = Alignment.Center

        ) {

            Icon(

                imageVector = item.icon,

                contentDescription = null,

                tint = iconColor,

                modifier = Modifier.size(24.dp)

            )

        }

        Spacer(

            Modifier.height(6.dp)

        )

        Text(

            text = item.screen.title,

            color = iconColor,

            fontSize = 11.sp,

            fontWeight =

                if (selected)

                    FontWeight.Bold

                else

                    FontWeight.Normal

        )

    }

}