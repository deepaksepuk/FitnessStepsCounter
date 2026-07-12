package madproject.deepaks3533898.fitnessstepscounter.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import madproject.deepaks3533898.fitnessstepscounter.data.local.DatabaseProvider
import madproject.deepaks3533898.fitnessstepscounter.data.local.StepSessionEntity
import madproject.deepaks3533898.fitnessstepscounter.data.repository.StepRepository
import madproject.deepaks3533898.fitnessstepscounter.model.StatisticsUiState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class StatisticsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = StepRepository(
        DatabaseProvider
            .getDatabase(application)
            .stepDao()
    )

    private val _uiState =
        MutableStateFlow(StatisticsUiState())

    val uiState: StateFlow<StatisticsUiState> =
        _uiState.asStateFlow()

    private val dateFormatter =
        SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
        )

    private val dayFormatter =
        SimpleDateFormat(
            "EEE",
            Locale.getDefault()
        )


    private var selectedWeekOffset = 0

    private var selectedMonthOffset = 0

    init {

        observeStatistics()

    }


    private fun observeStatistics() {

        viewModelScope.launch {

            repository.getAllSessions().collect { sessions ->

                if (sessions.isEmpty()) {

                    _uiState.value = StatisticsUiState()

                    return@collect

                }

                //-----------------------------
                // Summary
                //-----------------------------

                val totalSteps =
                    sessions.sumOf { it.steps }

                val totalDistance =
                    sessions.sumOf {
                        it.distance.toDouble()
                    }.toFloat()

                val totalCalories =
                    sessions.sumOf {
                        it.calories.toDouble()
                    }.toFloat()

                val averageSteps =
                    totalSteps / sessions.size

                val highest =
                    sessions.maxByOrNull {

                        it.steps

                    }

                //-----------------------------
                // Weekly Data
                //-----------------------------

                val weeklySessions =
                    mutableListOf<StepSessionEntity>()

                val calendar =
                    Calendar.getInstance()

                repeat(7) {

                    val date =
                        dateFormatter.format(calendar.time)

                    sessions.find {

                        it.date == date

                    }?.let {

                        weeklySessions.add(it)

                    }

                    calendar.add(
                        Calendar.DAY_OF_YEAR,
                        -1
                    )

                }

                weeklySessions.reverse()

                val weeklyLabels =
                    weeklySessions.map {

                        val date =
                            dateFormatter.parse(it.date)

                        dayFormatter.format(date!!)
                    }

                val weeklySteps =
                    weeklySessions.map {

                        it.steps

                    }

                //-----------------------------
                // Monthly Data
                //-----------------------------

                val monthMap =
                    LinkedHashMap<String, Int>()

                sessions.forEach { session ->

                    val month =
                        session.date.substring(3, 5)

                    monthMap[month] =
                        (monthMap[month] ?: 0) +
                                session.steps

                }

                val monthNames =
                    mapOf(

                        "01" to "Jan",
                        "02" to "Feb",
                        "03" to "Mar",
                        "04" to "Apr",
                        "05" to "May",
                        "06" to "Jun",
                        "07" to "Jul",
                        "08" to "Aug",
                        "09" to "Sep",
                        "10" to "Oct",
                        "11" to "Nov",
                        "12" to "Dec"

                    )

                val monthlyLabels =
                    monthMap.keys.map {

                        monthNames[it] ?: it

                    }

                val monthlySteps =
                    monthMap.values.toList()

                //-----------------------------
                // Update UI
                //-----------------------------

                _uiState.value = StatisticsUiState(

                    weeklyLabels = weeklyLabels,

                    weeklySteps = weeklySteps,

                    monthlyLabels = monthlyLabels,

                    monthlySteps = monthlySteps,

                    totalSteps = totalSteps,

                    totalDistance = totalDistance,

                    totalCalories = totalCalories,

                    averageSteps = averageSteps,

                    highestDay = highest?.date ?: "",

                    highestSteps = highest?.steps ?: 0

                )

            }

        }

    }

    // Keep your insertDummyData() function below this.

//    private val repository = StepRepository(
//
//        DatabaseProvider
//
//            .getDatabase(application)
//
//            .stepDao()
//
//    )

    fun insertDummyData() {

        viewModelScope.launch {

            val formatter = SimpleDateFormat(

                "dd/MM/yyyy",

                Locale.getDefault()

            )

            val calendar = Calendar.getInstance()

            calendar.add(Calendar.DAY_OF_YEAR, -59)

            val sessions = mutableListOf<StepSessionEntity>()

            repeat(60) {

                val day = calendar.get(Calendar.DAY_OF_WEEK)

                val isWeekend =

                    day == Calendar.SATURDAY ||

                            day == Calendar.SUNDAY

                val steps = when {

                    isWeekend ->

                        Random.nextInt(

                            9000,

                            16000

                        )

                    Random.nextInt(100) < 15 ->

                        Random.nextInt(

                            1500,

                            4500

                        )

                    else ->

                        Random.nextInt(

                            5000,

                            10000

                        )

                }

                val distance =

                    steps * 0.0008f

                val calories =

                    steps * 0.04f

                val duration =

                    steps / 2L

                sessions.add(

                    StepSessionEntity(

                        date = formatter.format(

                            calendar.time

                        ),

                        steps = steps,

                        distance = distance,

                        calories = calories,

                        duration = duration

                    )

                )

                calendar.add(

                    Calendar.DAY_OF_YEAR,

                    1

                )

            }

            repository.insertSessions(

                sessions

            )

        }

    }
}


