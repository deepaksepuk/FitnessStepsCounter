package madproject.deepaks3533898.fitnessstepscounter.ui


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import madproject.deepaks3533898.fitnessstepscounter.DashboardScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val backStack by navController.currentBackStackEntryAsState()

    val currentRoute = backStack?.destination?.route

    Scaffold(

        bottomBar = {

            NavigationBar {

                bottomItems.forEach { item ->

                    NavigationBarItem(

                        selected = currentRoute == item.screen.route,

                        onClick = {

                            navController.navigate(item.screen.route) {

                                popUpTo(
                                    navController.graph.findStartDestination().id
                                )

                                launchSingleTop = true

                                restoreState = true

                            }

                        },

                        icon = {

                            Icon(

                                item.icon,

                                null

                            )

                        },

                        label = {

                            Text(item.screen.title)

                        }

                    )

                }

            }

        }

    ) { padding ->

        NavHost(
            modifier = Modifier.padding(padding),

            navController = navController,

            startDestination = BtmScreen.Home.route

        ) {

            composable(BtmScreen.Home.route) {

                DashboardScreen(navController)

            }

            composable(BtmScreen.StartWalking.route) {

                StartWalkingScreen(navController)

            }

            composable(BtmScreen.History.route) {


                StatisticsScreen()

            }

            composable(BtmScreen.Goal.route) {

                Text("Goal BtmScreen")

                GoalSettingScreen()

            }

            composable(BtmScreen.BMI.route) {

                BMIScreen()

//                Text("BMI BtmScreen")

            }

            composable(BtmScreen.Profile.route) {

                Text("Profile Screen")

            }

        }

    }

}