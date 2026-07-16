package madproject.deepaks3533898.fitnessstepscounter.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import madproject.deepaks3533898.fitnessstepscounter.R
import madproject.deepaks3533898.fitnessstepscounter.viewmodel.GoalViewModel
import madproject.deepaks3533898.fitnessstepscounter.viewmodel.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalSettingScreen(

    viewModel: GoalViewModel = viewModel()

) {

    val state by viewModel.uiState.collectAsState()

    var stepGoal by remember(state.stepGoal) {
        mutableStateOf(state.stepGoal.toString())
    }

    var calorieGoal by remember(state.calorieGoal) {
        mutableStateOf(state.calorieGoal.toString())
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }



        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {

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

                    Box(

                        modifier = Modifier
                            .size(70.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                CircleShape
                            ),

                        contentAlignment = Alignment.Center

                    ) {

                        Image(

                            painter = painterResource(id = R.drawable.ic_goal_walk),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)

                        )

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(

                        "Set Your Daily Goals",

                        fontSize = 24.sp,

                        fontWeight = FontWeight.Bold

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(

                        "Stay motivated by setting your daily fitness targets.",

                        style = MaterialTheme.typography.bodyMedium

                    )

                }

            }

            GoalCard(

                title = "Daily Step Goal",

                icon = Icons.Default.DirectionsWalk

            ) {

                OutlinedTextField(

                    value = stepGoal,

                    onValueChange = {

                        stepGoal = it.filter { ch -> ch.isDigit() }

                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true,

                    label = {

                        Text("Enter Step Goal")

                    }

                )

            }

            GoalCard(

                title = "Daily Calorie Goal",

                icon = Icons.Default.LocalFireDepartment

            ) {

                OutlinedTextField(

                    value = calorieGoal,

                    onValueChange = {

                        calorieGoal = it.filter { ch -> ch.isDigit() }

                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true,

                    label = {

                        Text("Enter Calorie Goal")

                    }

                )

            }

            Button(

                onClick = {

                    viewModel.saveGoals(

                        stepGoal.toIntOrNull() ?: 10000,

                        calorieGoal.toIntOrNull() ?: 500

                    )

                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(

                    containerColor = MaterialTheme.colorScheme.primary

                )

            ) {

                Icon(

                    Icons.Default.Save,

                    contentDescription = null

                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(

                    "SAVE GOALS",

                    fontSize = 18.sp

                )

            }

        }

//    }

    LaunchedEffect(Unit) {

        viewModel.events.collect { event ->

            when (event) {

                is UiEvent.ShowSnackbar -> {

                    snackbarHostState.showSnackbar(
                        event.message
                    )

                }

            }

        }

    }

}

@Composable
fun GoalCard(

    title: String,

    icon: androidx.compose.ui.graphics.vector.ImageVector,

    content: @Composable () -> Unit

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 4.dp

        )

    ) {

        Column(

            modifier = Modifier.padding(20.dp)

        ) {

            Icon(

                icon,

                contentDescription = null,

                tint = MaterialTheme.colorScheme.primary

            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(

                title,

                fontWeight = FontWeight.Bold,

                fontSize = 18.sp

            )

            Spacer(modifier = Modifier.height(16.dp))

            content()

        }

    }

}