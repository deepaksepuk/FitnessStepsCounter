package madproject.deepaks3533898.fitnessstepscounter.ui


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import madproject.deepaks3533898.fitnessstepscounter.StatCard
import madproject.deepaks3533898.fitnessstepscounter.viewmodel.StepTrackerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartWalkingScreen(
    navController: NavHostController,
    viewModel: StepTrackerViewModel = viewModel()
) {

    val state by viewModel.uiState.collectAsState()


    val context = LocalContext.current

    var permissionGranted by remember {
        mutableStateOf(
            Build.VERSION.SDK_INT < Build.VERSION_CODES.Q ||
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACTIVITY_RECOGNITION
                    ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            permissionGranted = granted

        }

    LaunchedEffect(Unit) {

        if (!permissionGranted &&
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        ) {

            launcher.launch(
                Manifest.permission.ACTIVITY_RECOGNITION
            )

        }

    }


    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        "Start Walking",
                        fontWeight = FontWeight.Bold,
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )

                    }

                }

            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Text(

                text = "Live Step Counter",

                fontWeight = FontWeight.Bold,

                fontSize = 26.sp

            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(

                modifier = Modifier
                    .size(220.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        CircleShape
                    ),

                contentAlignment = Alignment.Center

            ) {

                Column(

                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Icon(

                        imageVector = Icons.Default.DirectionsWalk,

                        contentDescription = null,

                        modifier = Modifier.size(40.dp)

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(

                        text = "${state.steps}",

                        fontSize = 44.sp,

                        fontWeight = FontWeight.Bold

                    )

                    Text("Steps")

                }

            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) {

//                FitnessCard(
//
//                    modifier = Modifier.weight(1f),
//
//                    title = "Distance",
//
//                    value = String.format("%.2f km", state.distanceKm),
//
//                    icon = Icons.Default.Person
//
//                )

                StatCard(

                    modifier = Modifier.weight(1f),

                    title = "Distance\nCovered",

                    value = String.format("%.2f", state.distanceKm),

                    unit = "km",

                    icon = Icons.Default.Route,

                    iconBackground = Color(0xFFE8F5E9)

                )

//                FitnessCard(
//
//                    modifier = Modifier.weight(1f),
//
//                    title = "Calories",
//
//                    value = String.format("%.0f kcal", state.calories),
//
//                    icon = Icons.Default.Person
//
//                )

                StatCard(

                    modifier = Modifier.weight(1f),

                    title = "Calories\nBurnt",

                    value = String.format("%.0f", state.calories),

                    unit = "kCal",

                    icon = Icons.Default.LocalFireDepartment,

                    iconBackground = Color(0xFFFFF3E0)

                )

            }

            Spacer(modifier = Modifier.height(12.dp))

            FitnessCard(

                modifier = Modifier.fillMaxWidth(),

                title = "Activity Time",

                value = formatTime(state.durationSeconds),

                icon = Icons.Default.Timer

            )

            Spacer(modifier = Modifier.height(30.dp))

            if (!state.isTracking) {

                Button(

                    modifier = Modifier.fillMaxWidth(),

                    onClick = {

                        if (!viewModel.isSensorAvailable()) {

                            Toast.makeText(
                                context,
                                "Step Counter Sensor Not Available",
                                Toast.LENGTH_LONG
                            ).show()

                            return@Button
                        }

                        if (permissionGranted) {

                            viewModel.startTracking()

                        }
                    }

                ) {

                    Icon(Icons.Default.PlayArrow, null)

                    Spacer(modifier = Modifier.size(8.dp))

                    Text("START")

                }

            } else {

                Button(

                    modifier = Modifier.fillMaxWidth(),

                    onClick = {

                        viewModel.pauseTracking()

                    }

                ) {

                    Icon(Icons.Default.Person, null)

                    Spacer(modifier = Modifier.size(8.dp))

                    Text("PAUSE")

                }

            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(

                modifier = Modifier.fillMaxWidth(),

                onClick = {

                    if (state.isTracking)
                        viewModel.pauseTracking()
                    else
                        viewModel.resumeTracking()

                }

            ) {

                Text("RESUME")

            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(

                modifier = Modifier.fillMaxWidth(),

                onClick = {

                    viewModel.stopTracking()

                }

            ) {

                Icon(Icons.Default.Refresh, null)

                Spacer(modifier = Modifier.size(8.dp))

                Text("Save and Reset")

            }

        }

    }

    if (!permissionGranted) {

        Card(

            modifier = Modifier.fillMaxWidth()

        ) {

            Column(

                modifier = Modifier.padding(16.dp)

            ) {

                Text(

                    "Activity Recognition permission is required to count your steps.",

                    color = MaterialTheme.colorScheme.error

                )

            }

        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}

@Composable
fun FitnessCard(

    modifier: Modifier = Modifier,

    title: String,

    value: String,

    icon: androidx.compose.ui.graphics.vector.ImageVector

) {

    Card(

        modifier = modifier,

        colors = CardDefaults.cardColors(

            containerColor = MaterialTheme.colorScheme.surface

        )



    ) {

        Row(

            modifier = Modifier.padding(16.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Icon(

                imageVector = icon,

                contentDescription = null,

                tint = MaterialTheme.colorScheme.primary

            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(

                text = title,

                color = Color.Gray

            )

            Spacer(modifier = Modifier.weight(1f))

            Text(

                text = value,

                fontWeight = FontWeight.Bold,

                fontSize = 18.sp

            )

        }

    }

}

fun formatTime(

    seconds: Long

): String {

    val hrs = seconds / 3600

    val mins = (seconds % 3600) / 60

    val secs = seconds % 60

    return String.format(

        "%02d:%02d:%02d",

        hrs,

        mins,

        secs

    )

}