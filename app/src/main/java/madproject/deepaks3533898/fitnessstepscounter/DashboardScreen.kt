package madproject.deepaks3533898.fitnessstepscounter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.TimeUtils.formatDuration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import madproject.deepaks3533898.fitnessstepscounter.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = viewModel()
) {

    val latestSession by viewModel.latestSession.collectAsState()

    val steps = latestSession?.steps ?: 0
    val goal = 10000

    val progress =
        steps.toFloat() / goal

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Fitness Step Counter",
                        fontWeight = FontWeight.Bold
                    )

                }

            )

        }

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp)

        ) {

            Text(

                text = "Good Morning 👋",

                fontSize = 28.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(

                text = "Today's Progress",

                color = Color.Gray,

                fontSize = 16.sp

            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(

                modifier = Modifier.fillMaxWidth(),

                colors = CardDefaults.cardColors(

                    containerColor = MaterialTheme.colorScheme.primaryContainer

                )

            ) {

                Column(

                    modifier = Modifier.padding(20.dp),

                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Text(

                        text = "Today's Steps",

                        color = Color.Gray

                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(

                        text = "$steps",

                        fontSize = 48.sp,

                        fontWeight = FontWeight.Bold

                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(

                        progress = { progress },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(50))

                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("$steps / $goal Steps")

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(

                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                StatCard(

                    modifier = Modifier.weight(1f),

                    title = "Distance",

                    value = String.format(
                        "%.2f km",
                        latestSession?.distance ?: 0f
                    ),

                    icon = Icons.Default.Person

                )

                StatCard(

                    modifier = Modifier.weight(1f),

                    title = "Calories",

                    value = String.format(
                        "%.0f kcal",
                        latestSession?.calories ?: 0f
                    ),

                    icon = Icons.Default.Person

                )

            }

            Spacer(modifier = Modifier.height(12.dp))

            StatCard(

                modifier = Modifier.fillMaxWidth(),

                title = "Activity Time",

                value = formatDuration(
                    latestSession?.duration ?: 0L
                ),

                icon = Icons.Default.Person

            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(

                onClick = {
                    navController.navigate(Screen.StartWalking.route)
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)

            ) {

                Text(

                    text = "START WALKING",

                    fontSize = 18.sp

                )

            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(

                modifier = Modifier.fillMaxWidth()

            ) {

                Column(

                    modifier = Modifier.padding(16.dp)

                ) {

                    Text(

                        text = "Today's Goal",

                        fontWeight = FontWeight.Bold

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Goal : $goal Steps")

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        "Remaining : ${(goal - steps).coerceAtLeast(0)} Steps"
                    )
                }

            }

        }

    }

}

fun formatDuration(
    seconds: Long
): String {

    val hours = seconds / 3600

    val minutes = (seconds % 3600) / 60

    val secs = seconds % 60

    return if (hours > 0) {

        String.format(
            "%02d:%02d:%02d",
            hours,
            minutes,
            secs
        )

    } else {

        String.format(
            "%02d:%02d",
            minutes,
            secs
        )

    }

}

@Composable
fun StatCard(

    modifier: Modifier = Modifier,

    title: String,

    value: String,

    icon: androidx.compose.ui.graphics.vector.ImageVector

) {

    Card(

        modifier = modifier

    ) {

        Column(

            modifier = Modifier.padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Box(

                modifier = Modifier
                    .size(50.dp)
                    .background(

                        MaterialTheme.colorScheme.primaryContainer,

                        CircleShape

                    ),

                contentAlignment = Alignment.Center

            ) {

                Icon(

                    imageVector = icon,

                    contentDescription = null

                )

            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(

                text = title,

                color = Color.Gray

            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(

                text = value,

                fontWeight = FontWeight.Bold,

                fontSize = 18.sp

            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {

    MaterialTheme {

//        DashboardScreen()

    }

}