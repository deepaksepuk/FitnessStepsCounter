package madproject.deepaks3533898.fitnessstepscounter.ui


import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import madproject.deepaks3533898.fitnessstepscounter.AppUserData
import madproject.deepaks3533898.fitnessstepscounter.viewmodel.StatisticsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = viewModel()
) {

    val state by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {

        if (!AppUserData.getDataGeneratedStatus(context)) {
            viewModel.insertDummyData()
            AppUserData.saveDataGeneratedStatus(context,true)
        }

    }

    var selectedTab by remember {

        mutableIntStateOf(0)

    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("Statistics")

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

            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {

            HeroStatisticsCard()

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                FilterChip(

                    selected = selectedTab == 0,

                    onClick = {

                        selectedTab = 0

                    },

                    label = {

                        Text("Weekly")

                    },

                    leadingIcon = {

                        Icon(

                            Icons.Default.ShowChart,

                            null

                        )

                    },

                    colors = FilterChipDefaults.filterChipColors()

                )

                FilterChip(

                    selected = selectedTab == 1,

                    onClick = {

                        selectedTab = 1

                    },

                    label = {

                        Text("Monthly")

                    },

                    leadingIcon = {

                        Icon(

                            Icons.Default.TrendingUp,

                            null

                        )

                    }

                )

            }

            ChartCard(

                title = if (selectedTab == 0)
                    "Weekly Progress"
                else
                    "Monthly Progress"

            ) {

                if (selectedTab == 0) {

                    FitnessChart(

                        labels = state.weeklyLabels,

                        values = state.weeklySteps,

                        chartType = ChartType.LINE

                    )

                } else {

                    FitnessChart(

                        labels = state.monthlyLabels,

                        values = state.monthlySteps,

                        chartType = ChartType.BAR

                    )

                }

            }

            Row(

                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                SummaryCard(

                    modifier = Modifier.weight(1f),

                    title = "Steps",

                    value = state.totalSteps.toString(),

                    icon = Icons.Default.DirectionsWalk

                )

                SummaryCard(

                    modifier = Modifier.weight(1f),

                    title = "Distance",

                    value = String.format(
                        "%.1f km",
                        state.totalDistance
                    ),

                    icon = Icons.Default.Route

                )

            }

            Row(

                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                SummaryCard(

                    modifier = Modifier.weight(1f),

                    title = "Calories",

                    value = String.format(
                        "%.0f kcal",
                        state.totalCalories
                    ),

                    icon = Icons.Default.LocalFireDepartment

                )

                SummaryCard(

                    modifier = Modifier.weight(1f),

                    title = "Average",

                    value = "${state.averageSteps}",

                    icon = Icons.Default.TrendingUp

                )

            }

            HighestDayCard(

                day = state.highestDay,

                steps = state.highestSteps

            )

        }

    }

}


@Composable
fun HeroStatisticsCard() {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(

            containerColor = MaterialTheme.colorScheme.primaryContainer

        )

    ) {

        Column(

            modifier = Modifier.padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Icon(

                imageVector = Icons.Default.ShowChart,

                contentDescription = null,

                modifier = Modifier.size(60.dp),

                tint = MaterialTheme.colorScheme.primary

            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(

                text = "Fitness Statistics",

                fontSize = 24.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(

                text = "Track your weekly and monthly progress to stay motivated and achieve your fitness goals.",

                style = MaterialTheme.typography.bodyMedium

            )

        }

    }

}

@Composable
fun ChartCard(

    title: String,

    content: @Composable () -> Unit

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 5.dp

        )

    ) {

        Column(

            modifier = Modifier.padding(20.dp)

        ) {

            Text(

                text = title,

                fontSize = 20.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(20.dp))

            content()

        }

    }

}

@Composable
fun SummaryCard(

    modifier: Modifier = Modifier,

    title: String,

    value: String,

    icon: ImageVector

) {

    Card(

        modifier = modifier,

        shape = RoundedCornerShape(18.dp),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 4.dp

        )

    ) {

        Column(

            modifier = Modifier.padding(18.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Icon(

                imageVector = icon,

                contentDescription = null,

                tint = MaterialTheme.colorScheme.primary,

                modifier = Modifier.size(34.dp)

            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(

                text = title,

                color = MaterialTheme.colorScheme.onSurfaceVariant

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(

                text = value,

                fontSize = 22.sp,

                fontWeight = FontWeight.Bold

            )

        }

    }

}

@Composable
fun HighestDayCard(

    day: String,

    steps: Int

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(

            containerColor = MaterialTheme.colorScheme.secondaryContainer

        )

    ) {

        Column(

            modifier = Modifier.padding(20.dp)

        ) {

            Text(

                text = "Best Performance",

                fontSize = 20.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(

                verticalAlignment = Alignment.CenterVertically

            ) {

                Icon(

                    imageVector = Icons.Default.TrendingUp,

                    contentDescription = null,

                    tint = MaterialTheme.colorScheme.primary

                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {

                    Text(

                        text = day,

                        fontWeight = FontWeight.Bold

                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(

                        text = "$steps Steps"

                    )

                }

            }

        }

    }

}