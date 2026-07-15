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

    private var statisticsMode =
        StatisticsMode.WEEK

    private var isWeeklySelected = true
    /**
     * Holds all sessions from Room.
     * We collect Room only once.
     */
    private var allSessions =
        emptyList<StepSessionEntity>()

    /**
     * 0 = Current Week
     * 1 = Previous Week
     * 2 = Two Weeks Ago
     */
    private var selectedWeekOffset = 0

    /**
     * 0 = Current Month
     * 1 = Previous Month
     * 2 = Two Months Ago
     */
    private var selectedMonthOffset = 0

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

    private val weekTitleFormatter =
        SimpleDateFormat(
            "dd MMM",
            Locale.getDefault()
        )

    private val monthTitleFormatter =
        SimpleDateFormat(
            "MMMM yyyy",
            Locale.getDefault()
        )

    private val chartDateFormatter =
        SimpleDateFormat(
            "dd MMM",
            Locale.getDefault()
        )

    init {

        observeDatabase()

    }


    fun showWeekly() {

        statisticsMode =
            StatisticsMode.WEEK

        updateStatistics()

    }

    fun showMonthly() {

        statisticsMode =
            StatisticsMode.MONTH

        updateStatistics()

    }

    /**
     * Observe Room only once.
     */
    private fun observeDatabase() {

        viewModelScope.launch {

            repository.getAllSessions().collect { sessions ->

                allSessions = sessions

                updateStatistics()

            }

        }

    }

    /**
     * Previous Week
     */
    fun previousWeek() {

        selectedWeekOffset++

        updateStatistics()

    }

    /**
     * Next Week
     */
    fun nextWeek() {

        if (selectedWeekOffset > 0) {

            selectedWeekOffset--

            updateStatistics()

        }

    }

    /**
     * Previous Month
     */
    fun previousMonth() {

        selectedMonthOffset++

        updateStatistics()

    }

    /**
     * Next Month
     */
    fun nextMonth() {

        if (selectedMonthOffset > 0) {

            selectedMonthOffset--

            updateStatistics()

        }

    }

    fun changeMode(

        isWeekly: Boolean

    ) {

        isWeeklySelected = isWeekly

        updateStatistics()

    }

    /**
     * Recalculate all statistics.
     * (Implemented in Part 2)
     */
    private fun updateStatistics() {

        if (allSessions.isEmpty()) {

            _uiState.value = StatisticsUiState()

            return

        }

        val sessionMap =
            allSessions.associateBy { it.date }

        //--------------------------------------------------
        // WEEK
        //--------------------------------------------------

        val weekCalendar = Calendar.getInstance()

        weekCalendar.add(
            Calendar.WEEK_OF_YEAR,
            -selectedWeekOffset
        )

        weekCalendar.set(
            Calendar.DAY_OF_WEEK,
            weekCalendar.firstDayOfWeek
        )

        val weekStart =
            weekCalendar.time

        val weeklyLabels =
            mutableListOf<String>()

        val weeklySteps =
            mutableListOf<Int>()

        var weekSteps = 0
        var weekDistance = 0f
        var weekCalories = 0f

        var highestSteps = 0
        var highestDay = ""

        repeat(7) {

            val dateString =
                dateFormatter.format(
                    weekCalendar.time
                )

            val session =
                sessionMap[dateString]

            val steps =
                session?.steps ?: 0

            weeklySteps.add(steps)

//            weeklyLabels.add(
//
//                dayFormatter.format(
//                    weekCalendar.time
//                )
//
//            )

            weeklyLabels.add(
                chartDateFormatter.format(
                    weekCalendar.time
                )
            )

            weekSteps += steps

            weekDistance +=
                session?.distance ?: 0f

            weekCalories +=
                session?.calories ?: 0f

            if (steps > highestSteps) {

                highestSteps = steps

                highestDay = dateString

            }

            weekCalendar.add(
                Calendar.DAY_OF_YEAR,
                1
            )

        }

        val weekEndCalendar =
            Calendar.getInstance()

        weekEndCalendar.time = weekStart

        weekEndCalendar.add(
            Calendar.DAY_OF_YEAR,
            6
        )

        val weekTitle =

            "${weekTitleFormatter.format(weekStart)} - " +
                    weekTitleFormatter.format(
                        weekEndCalendar.time
                    )

        //--------------------------------------------------
        // MONTH
        //--------------------------------------------------

        val monthCalendar =
            Calendar.getInstance()

        monthCalendar.add(
            Calendar.MONTH,
            -selectedMonthOffset
        )

        val monthTitle =
            monthTitleFormatter.format(
                monthCalendar.time
            )

        val targetMonth =
            monthCalendar.get(Calendar.MONTH)

        val targetYear =
            monthCalendar.get(Calendar.YEAR)

        val monthlyLabels =
            mutableListOf<String>()

        val monthlySteps =
            mutableListOf<Int>()

        val daysInMonth =
            monthCalendar.getActualMaximum(
                Calendar.DAY_OF_MONTH
            )

        var monthSteps = 0

        var monthDistance = 0f

        var monthCalories = 0f

        var monthHighestSteps = 0

        var monthHighestDay = ""

        repeat(daysInMonth) { index ->

            val day = index + 1

            monthCalendar.set(
                Calendar.DAY_OF_MONTH,
                day
            )

            val dateString =
                dateFormatter.format(
                    monthCalendar.time
                )

            val session =
                sessionMap[dateString]

            monthlyLabels.add(day.toString())

            monthlySteps.add(
                session?.steps ?: 0
            )

            monthSteps +=
                session?.steps ?: 0

            monthDistance += session?.distance ?: 0f

            monthCalories += session?.calories ?: 0f

            if ((session?.steps ?: 0) > monthHighestSteps) {

                monthHighestSteps = session!!.steps

                monthHighestDay = dateString

            }

        }

        //--------------------------------------------------
        // SUMMARY
        //--------------------------------------------------

        val averageSteps =

            if (weekSteps == 0)
                0
            else
                weekSteps / 7

        //--------------------------------------------------
        // UI STATE
        //--------------------------------------------------

        _uiState.value = StatisticsUiState(

            // -------- Chart Data --------

            weeklyLabels = weeklyLabels,

            weeklySteps = weeklySteps,

            monthlyLabels = monthlyLabels,

            monthlySteps = monthlySteps,

            // -------- Summary --------

            totalSteps =
                if (isWeeklySelected)
                    weekSteps
                else
                    monthSteps,

            totalDistance =
                if (isWeeklySelected)
                    weekDistance
                else
                    monthDistance,

            totalCalories =
                if (isWeeklySelected)
                    weekCalories
                else
                    monthCalories,

            averageSteps =
                if (isWeeklySelected)
                    if (weekSteps == 0) 0 else weekSteps / 7
                else
                    if (monthSteps == 0) 0 else monthSteps / daysInMonth,

            highestDay =
                if (isWeeklySelected)
                    highestDay
                else
                    monthHighestDay,

            highestSteps =
                if (isWeeklySelected)
                    highestSteps
                else
                    monthHighestSteps,

            // -------- Navigation --------

            currentWeekTitle = weekTitle,

            currentMonthTitle = monthTitle,

            canGoNextWeek = selectedWeekOffset > 0,

            canGoNextMonth = selectedMonthOffset > 0

        )

    }

    // insertDummyData() will remain below.


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


enum class StatisticsMode {
    WEEK,
    MONTH
}

//class StatisticsViewModel(
//    application: Application
//) : AndroidViewModel(application) {
//
//    private val repository = StepRepository(
//        DatabaseProvider
//            .getDatabase(application)
//            .stepDao()
//    )
//
//    private val _uiState =
//        MutableStateFlow(StatisticsUiState())
//
//    val uiState: StateFlow<StatisticsUiState> =
//        _uiState.asStateFlow()
//
//    private val dateFormatter =
//        SimpleDateFormat(
//            "dd/MM/yyyy",
//            Locale.getDefault()
//        )
//
//    private val dayFormatter =
//        SimpleDateFormat(
//            "EEE",
//            Locale.getDefault()
//        )
//
//
//    private var selectedWeekOffset = 0
//
//    private var selectedMonthOffset = 0
//
//    init {
//
//        observeStatistics()
//
//    }
//
//
//    private fun observeStatistics() {
//
//        viewModelScope.launch {
//
//            repository.getAllSessions().collect { sessions ->
//
//                if (sessions.isEmpty()) {
//
//                    _uiState.value = StatisticsUiState()
//
//                    return@collect
//
//                }
//
//                //-----------------------------
//                // Summary
//                //-----------------------------
//
//                val totalSteps =
//                    sessions.sumOf { it.steps }
//
//                val totalDistance =
//                    sessions.sumOf {
//                        it.distance.toDouble()
//                    }.toFloat()
//
//                val totalCalories =
//                    sessions.sumOf {
//                        it.calories.toDouble()
//                    }.toFloat()
//
//                val averageSteps =
//                    totalSteps / sessions.size
//
//                val highest =
//                    sessions.maxByOrNull {
//
//                        it.steps
//
//                    }
//
//                //-----------------------------
//                // Weekly Data
//                //-----------------------------
//
//                val weeklySessions =
//                    mutableListOf<StepSessionEntity>()
//
//                val calendar =
//                    Calendar.getInstance()
//
//                repeat(7) {
//
//                    val date =
//                        dateFormatter.format(calendar.time)
//
//                    sessions.find {
//
//                        it.date == date
//
//                    }?.let {
//
//                        weeklySessions.add(it)
//
//                    }
//
//                    calendar.add(
//                        Calendar.DAY_OF_YEAR,
//                        -1
//                    )
//
//                }
//
//                weeklySessions.reverse()
//
//                val weeklyLabels =
//                    weeklySessions.map {
//
//                        val date =
//                            dateFormatter.parse(it.date)
//
//                        dayFormatter.format(date!!)
//                    }
//
//                val weeklySteps =
//                    weeklySessions.map {
//
//                        it.steps
//
//                    }
//
//                //-----------------------------
//                // Monthly Data
//                //-----------------------------
//
//                val monthMap =
//                    LinkedHashMap<String, Int>()
//
//                sessions.forEach { session ->
//
//                    val month =
//                        session.date.substring(3, 5)
//
//                    monthMap[month] =
//                        (monthMap[month] ?: 0) +
//                                session.steps
//
//                }
//
//                val monthNames =
//                    mapOf(
//
//                        "01" to "Jan",
//                        "02" to "Feb",
//                        "03" to "Mar",
//                        "04" to "Apr",
//                        "05" to "May",
//                        "06" to "Jun",
//                        "07" to "Jul",
//                        "08" to "Aug",
//                        "09" to "Sep",
//                        "10" to "Oct",
//                        "11" to "Nov",
//                        "12" to "Dec"
//
//                    )
//
//                val monthlyLabels =
//                    monthMap.keys.map {
//
//                        monthNames[it] ?: it
//
//                    }
//
//                val monthlySteps =
//                    monthMap.values.toList()
//
//                //-----------------------------
//                // Update UI
//                //-----------------------------
//
//                _uiState.value = StatisticsUiState(
//
//                    weeklyLabels = weeklyLabels,
//
//                    weeklySteps = weeklySteps,
//
//                    monthlyLabels = monthlyLabels,
//
//                    monthlySteps = monthlySteps,
//
//                    totalSteps = totalSteps,
//
//                    totalDistance = totalDistance,
//
//                    totalCalories = totalCalories,
//
//                    averageSteps = averageSteps,
//
//                    highestDay = highest?.date ?: "",
//
//                    highestSteps = highest?.steps ?: 0
//
//                )
//
//            }
//
//        }
//
//    }
//
//    // Keep your insertDummyData() function below this.
//
////    private val repository = StepRepository(
////
////        DatabaseProvider
////
////            .getDatabase(application)
////
////            .stepDao()
////
////    )
//
//    fun insertDummyData() {
//
//        viewModelScope.launch {
//
//            val formatter = SimpleDateFormat(
//
//                "dd/MM/yyyy",
//
//                Locale.getDefault()
//
//            )
//
//            val calendar = Calendar.getInstance()
//
//            calendar.add(Calendar.DAY_OF_YEAR, -59)
//
//            val sessions = mutableListOf<StepSessionEntity>()
//
//            repeat(60) {
//
//                val day = calendar.get(Calendar.DAY_OF_WEEK)
//
//                val isWeekend =
//
//                    day == Calendar.SATURDAY ||
//
//                            day == Calendar.SUNDAY
//
//                val steps = when {
//
//                    isWeekend ->
//
//                        Random.nextInt(
//
//                            9000,
//
//                            16000
//
//                        )
//
//                    Random.nextInt(100) < 15 ->
//
//                        Random.nextInt(
//
//                            1500,
//
//                            4500
//
//                        )
//
//                    else ->
//
//                        Random.nextInt(
//
//                            5000,
//
//                            10000
//
//                        )
//
//                }
//
//                val distance =
//
//                    steps * 0.0008f
//
//                val calories =
//
//                    steps * 0.04f
//
//                val duration =
//
//                    steps / 2L
//
//                sessions.add(
//
//                    StepSessionEntity(
//
//                        date = formatter.format(
//
//                            calendar.time
//
//                        ),
//
//                        steps = steps,
//
//                        distance = distance,
//
//                        calories = calories,
//
//                        duration = duration
//
//                    )
//
//                )
//
//                calendar.add(
//
//                    Calendar.DAY_OF_YEAR,
//
//                    1
//
//                )
//
//            }
//
//            repository.insertSessions(
//
//                sessions
//
//            )
//
//        }
//
//    }
//}


