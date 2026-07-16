package madproject.deepaks3533898.fitnessstepscounter.ui


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
            AppUserData.saveDataGeneratedStatus(context, true)
        }

    }

    var selectedTab by remember {

        mutableIntStateOf(0)

    }

//    Scaffold(
//
//        topBar = {
//
//            TopAppBar(
//
//                title = {
//
//                    Text("Statistics")
//
//                }
//
//            )
//
//        }
//
//    ) { padding ->

    Column(

        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {

        HeroStatisticsCard()

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFFF3F6FB))
                .padding(4.dp),

            horizontalArrangement = Arrangement.spacedBy(4.dp)

        ) {

            StatisticsTab(

                modifier = Modifier.weight(1f),

                selected = selectedTab == 0,

                title = "Weekly",

                icon = Icons.Default.DateRange

            ) {

                selectedTab = 0

                viewModel.changeMode(true)

            }

            StatisticsTab(

                modifier = Modifier.weight(1f),

                selected = selectedTab == 1,

                title = "Monthly",

                icon = Icons.Default.CalendarMonth

            ) {

                selectedTab = 1

                viewModel.changeMode(false)

            }

        }

        ChartCard(

            title = if (selectedTab == 0)
                "Weekly Progress"
            else
                "Monthly Progress"

        ) {

            ChartNavigation(

                isWeekly = selectedTab == 0,

                title =
                    if (selectedTab == 0)
                        state.currentWeekTitle
                    else
                        state.currentMonthTitle,

                canGoNext =
                    if (selectedTab == 0)
                        state.canGoNextWeek
                    else
                        state.canGoNextMonth,

                onPrevious = {

                    if (selectedTab == 0)

                        viewModel.previousWeek()
                    else

                        viewModel.previousMonth()

                },

                onNext = {

                    if (selectedTab == 0)

                        viewModel.nextWeek()
                    else

                        viewModel.nextMonth()

                }

            )

            Spacer(

                modifier = Modifier.height(20.dp)

            )

            FitnessChart(

                labels =
                    if (selectedTab == 0)
                        state.weeklyLabels
                    else
                        state.monthlyLabels,

                values =
                    if (selectedTab == 0)
                        state.weeklySteps
                    else
                        state.monthlySteps,

                chartType =
                    if (selectedTab == 0)
                        ChartType.LINE
                    else
                        ChartType.BAR

            )

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

//    }

}


@Composable
fun StatisticsTab(

    modifier: Modifier = Modifier,

    selected: Boolean,

    title: String,

    icon: ImageVector,

    onClick: () -> Unit

) {

    val background by animateColorAsState(

        if (selected)

            MaterialTheme.colorScheme.primary

        else

            Color.Transparent,

        label = ""

    )

    val textColor by animateColorAsState(

        if (selected)

            Color.White

        else

            MaterialTheme.colorScheme.primary,

        label = ""

    )

    Card(

        modifier = modifier
            .height(52.dp)
            .clickable {

                onClick()

            },

        colors = CardDefaults.cardColors(

            containerColor = background

        ),

        elevation = CardDefaults.cardElevation(

            defaultElevation =

                if (selected) 4.dp else 0.dp

        ),

        shape = RoundedCornerShape(14.dp)

    ) {

        Row(

            modifier = Modifier.fillMaxSize(),

            horizontalArrangement = Arrangement.Center,

            verticalAlignment = Alignment.CenterVertically

        ) {

            Icon(

                imageVector = icon,

                contentDescription = null,

                tint = textColor,

                modifier = Modifier.size(20.dp)

            )

            Spacer(

                Modifier.width(8.dp)

            )

            Text(

                text = title,

                color = textColor,

                fontWeight = FontWeight.SemiBold,

                fontSize = 15.sp

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
fun ChartNavigation(

    isWeekly: Boolean,

    title: String,

    canGoNext: Boolean,

    onPrevious: () -> Unit,

    onNext: () -> Unit

) {

    Row(

        modifier = Modifier.fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically,

        horizontalArrangement =
            Arrangement.SpaceBetween

    ) {

        IconButton(

            onClick = onPrevious

        ) {

            Icon(

                Icons.Default.KeyboardArrowLeft,

                null

            )

        }

        Column(

            horizontalAlignment =
                Alignment.CenterHorizontally

        ) {

            Text(

                if (isWeekly)
                    "Week"
                else
                    "Month",

                color =
                    MaterialTheme.colorScheme.primary

            )

            Spacer(

                modifier = Modifier.height(4.dp)

            )

            Text(

                title,

                fontWeight =
                    FontWeight.Bold,

                fontSize = 18.sp

            )

        }

        IconButton(

            enabled = canGoNext,

            onClick = onNext

        ) {

            Icon(

                Icons.Default.KeyboardArrowRight,

                null

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