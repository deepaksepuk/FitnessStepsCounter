package madproject.deepaks3533898.fitnessstepscounter

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.TimeUtils.formatDuration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import madproject.deepaks3533898.fitnessstepscounter.theme.C2
import madproject.deepaks3533898.fitnessstepscounter.viewmodel.DashboardViewModel
import madproject.deepaks3533898.fitnessstepscounter.viewmodel.GoalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = viewModel(),
    goalViewModel: GoalViewModel = viewModel()
) {

    val latestSession by viewModel.latestSession.collectAsState()

    val steps = latestSession?.steps ?: 0

    val goalState by goalViewModel.uiState.collectAsState()
    val goal = goalState.stepGoal

    val caloriesState by goalViewModel.uiState.collectAsState()
    val caloriesGoal = goalState.calorieGoal

    val progress =
        steps.toFloat() / goal

    val water by viewModel.water.collectAsState()

    val context = LocalContext.current

    Column(

        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)

    ) {

        Text(

            text = "Hi ,${AppUserData.getUserName(context)} 👋",

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


        GoalProgressCard(
            stepGoal = goal,
            currentSteps = steps,
            calorieGoal = caloriesGoal,
            currentCalories = latestSession?.calories?.div(1000f) ?: 0f,
            onStartWalking = {
                navController.navigate(Screen.StartWalking.route)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(

            onClick = {
                navController.navigate(Screen.StartWalking.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = C2
            ),

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)

        ) {

            Text(

                text = "START WALKING",

                fontSize = 18.sp

            )

        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(

            horizontalArrangement = Arrangement.spacedBy(12.dp)

        ) {



            StatCard(

                modifier = Modifier.weight(1f),

                title = "Distance\nCovered",

                value = String.format("%.1f", latestSession?.distance ?: 0f),

                unit = "km",

                icon = Icons.Default.Route,

                iconBackground = Color(0xFFE8F5E9)

            )

            StatCard(

                modifier = Modifier.weight(1f),

                title = "Calories\nBurnt",

                value = String.format("%.1f", latestSession?.calories?.div(1000f) ?: 0f),

                unit = "kCal",

                icon = Icons.Default.LocalFireDepartment,

                iconBackground = Color(0xFFFFF3E0)

            )

//            StatCard(
//
//                modifier = Modifier.weight(1f),
//
//                title = "Calories",
//
//                value = String.format(
//                    "%.0f kcal",
//                    latestSession?.calories ?: 0f
//                ),
//
//                icon = Icons.Default.Person
//
//            )

        }

        Spacer(modifier = Modifier.height(12.dp))

//        StatCard(
//
//            modifier = Modifier.fillMaxWidth(),
//
//            title = "Activity Time",
//
//            value = formatDuration(
//                latestSession?.duration ?: 0L
//            ),
//
//            icon = Icons.Default.Person
//        )

        Row(

            horizontalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            StatCard(

                modifier = Modifier.weight(1f),

                title = "Activity\nTime",

                value = formatDuration(
                    latestSession?.duration ?: 0L
                ),

                unit = "",

                icon = Icons.Default.Schedule,

                iconBackground = Color(0xFFE3F2FD)

            )

            WaterCard(

                modifier = Modifier.weight(1f),

                glasses = water,

                onAddWater = {

                    viewModel.addWater()

                }

            )

        }

        Spacer(modifier = Modifier.height(16.dp))


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




    }

//    }

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

    unit: String,

    icon: ImageVector,

    iconBackground: Color = MaterialTheme.colorScheme.primaryContainer

) {

    Card(

        modifier = modifier
            .height(160.dp),

        shape = RoundedCornerShape(24.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),

            verticalArrangement = Arrangement.SpaceBetween

        ) {

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,

                verticalAlignment = Alignment.Top

            ) {

                Text(

                    text = title,

                    fontSize = 18.sp,

                    fontWeight = FontWeight.Medium,

                    lineHeight = 26.sp

                )

                Box(

                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(iconBackground),

                    contentAlignment = Alignment.Center

                ) {

                    Icon(

                        imageVector = icon,

                        contentDescription = null,

                        tint = MaterialTheme.colorScheme.primary

                    )

                }

            }

            Row(

                verticalAlignment = Alignment.Bottom

            ) {

                Text(

                    text = value,

                    fontSize = 28.sp,

                    fontWeight = FontWeight.Bold

                )

                Spacer(
                    modifier = Modifier.width(6.dp)
                )

                Text(

                    text = unit,

                    fontSize = 20.sp,

                    color = Color.Gray,

                    modifier = Modifier.padding(bottom = 4.dp)

                )

            }

        }

    }

}

@Composable
fun WaterCard(

    modifier: Modifier = Modifier,

    glasses: Int,

    onAddWater: () -> Unit

) {

    val progress by animateFloatAsState(

        targetValue = glasses / 8f,

        label = ""

    )

    Card(

        modifier = modifier
            .height(160.dp)
            .clickable {
                if (glasses < 8)
                    onAddWater()
            },

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),

            verticalArrangement = Arrangement.SpaceBetween

        ) {

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,

                verticalAlignment = Alignment.CenterVertically

            ) {

                Text(

                    text = "Water\nIntake",

                    fontSize = 14.sp,

                    fontWeight = FontWeight.Medium

                )

                Box(

                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            Color(0xFFE3F2FD)
                        ),

                    contentAlignment = Alignment.Center

                ) {

                    Icon(

                        imageVector = Icons.Default.WaterDrop,

                        contentDescription = null,

                        tint = Color(0xFF1976D2)

                    )

                }

            }

            Column {

                Row(

                    verticalAlignment = Alignment.Bottom

                ) {

                    Text(

                        text = "$glasses",

                        fontSize = 20.sp,

                        fontWeight = FontWeight.Bold

                    )

                    Spacer(
                        modifier = Modifier.width(6.dp)
                    )

                    Text(

                        text = "/ 8 Glasses",

                        fontSize = 16.sp,

                        color = Color.Gray,

//                        modifier = Modifier.padding(bottom = 5.dp)

                    )

                }

//                Spacer(
//                    modifier = Modifier.height(10.dp)
//                )



                Text(

                    text =

                        if (glasses == 8)

                            "Daily Goal Completed 🎉"

                        else

                            "Tap to add a glass",

                    fontSize = 13.sp,

                    color =

                        if (glasses == 8)

                            Color(0xFF2E7D32)

                        else

                            Color.Gray

                )

                LinearProgressIndicator(

                    progress = { progress },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(50.dp))

                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

            }

        }

    }

}


@Composable
fun GoalProgressCard(

    modifier: Modifier = Modifier,

    stepGoal: Int,

    currentSteps: Int,

    calorieGoal: Int,

    currentCalories: Float,

    onStartWalking: () -> Unit

) {

    val stepProgress by animateFloatAsState(

        targetValue =

            (currentSteps.toFloat() / stepGoal)
                .coerceIn(0f, 1f),

        label = ""

    )

    val calorieProgress by animateFloatAsState(

        targetValue =

            (currentCalories.toFloat() / calorieGoal)
                .coerceIn(0f, 1f),

        label = ""

    )

    Card(

        modifier = modifier.fillMaxWidth(),

        shape = RoundedCornerShape(28.dp),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 6.dp

        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Column(

                modifier = Modifier.weight(1f)

            ) {

                Text(

                    text = "Today's Goals",

                    fontSize = 24.sp,

                    fontWeight = FontWeight.Bold

                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                GoalProgressItem(

                    title = "Step Goal",

                    icon = Icons.Default.DirectionsWalk,

                    progress = stepProgress,

                    progressColor = Color(0xFF2962FF),

                    value =
                        "$currentSteps / $stepGoal"

                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                GoalProgressItem(

                    title = "Calories Goal",

                    icon = Icons.Default.LocalFireDepartment,

                    progress = calorieProgress,

                    progressColor = Color(0xFFFF9800),

                    value =
                        "$currentCalories / $calorieGoal kcal"

                )

            }

//            Spacer(
//                modifier = Modifier.width(20.dp)
//            )

//            StartWalkingCircleButton(
//
//                onClick = onStartWalking
//
//            )

        }

    }

}

@Composable
fun StartWalkingCircleButton(

    onClick: () -> Unit

) {

    Card(

        modifier = Modifier
            .size(135.dp)
            .clickable {

                onClick()

            },

        shape = CircleShape,

        elevation = CardDefaults.cardElevation(

            defaultElevation = 10.dp

        ),

        colors = CardDefaults.cardColors(

            containerColor = Color(0xFF2962FF)

        )

    ) {

        Box(

            modifier = Modifier.fillMaxSize(),

            contentAlignment = Alignment.Center

        ) {

            Column(

                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Box(

                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(
                            Color.White.copy(alpha = .18f)
                        ),

                    contentAlignment = Alignment.Center

                ) {

                    Icon(

                        imageVector = Icons.Default.PlayArrow,

                        contentDescription = null,

                        tint = Color.White,

                        modifier = Modifier.size(34.dp)

                    )

                }

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(

                    text = "START",

                    color = Color.White,

                    fontWeight = FontWeight.Bold,

                    fontSize = 18.sp

                )

                Text(

                    text = "WALKING",

                    color = Color.White,

                    fontWeight = FontWeight.Medium,

                    fontSize = 13.sp,

                    letterSpacing = 1.sp

                )

            }

        }

    }

}


@Composable
fun GoalProgressItem(

    title: String,

    value: String,

    progress: Float,

    progressColor: Color,

    icon: ImageVector

) {

    Column {

        Row(

            verticalAlignment = Alignment.CenterVertically

        ) {

            Box(

                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(progressColor.copy(alpha = 0.12f)),

                contentAlignment = Alignment.Center

            ) {

                Icon(

                    imageVector = icon,

                    contentDescription = null,

                    tint = progressColor,

                    modifier = Modifier.size(22.dp)

                )

            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(

                modifier = Modifier.weight(1f)

            ) {

                Text(

                    text = title,

                    fontSize = 16.sp,

                    fontWeight = FontWeight.SemiBold

                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(

                    text = value,

                    color = Color.Gray,

                    fontSize = 13.sp

                )

            }

            Text(

                text = "${(progress * 100).toInt()}%",

                color = progressColor,

                fontWeight = FontWeight.Bold,

                fontSize = 16.sp

            )

        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Box(

            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFEAEAEA))

        ) {

            Box(

                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .clip(RoundedCornerShape(50))
                    .background(progressColor)

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