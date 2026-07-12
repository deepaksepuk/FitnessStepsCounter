package madproject.deepaks3533898.fitnessstepscounter.model


data class StatisticsUiState(

    val weeklyLabels: List<String> = emptyList(),

    val weeklySteps: List<Int> = emptyList(),

    val monthlyLabels: List<String> = emptyList(),

    val monthlySteps: List<Int> = emptyList(),

    val totalSteps: Int = 0,

    val totalDistance: Float = 0f,

    val totalCalories: Float = 0f,

    val averageSteps: Int = 0,

    val highestDay: String = "",

    val highestSteps: Int = 0,

    val currentWeekTitle: String = "",

    val currentMonthTitle: String = "",

    val canGoNextWeek: Boolean = false,

    val canGoNextMonth: Boolean = false

)