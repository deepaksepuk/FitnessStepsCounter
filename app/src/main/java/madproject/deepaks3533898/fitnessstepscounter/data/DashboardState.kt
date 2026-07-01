package madproject.deepaks3533898.fitnessstepscounter.data

data class DashboardState(

    val steps: Int = 0,

    val distance: Float = 0f,

    val calories: Int = 0,

    val duration: Long = 0,

    val goal: Int = 10000,

    val progress: Float = 0f,

    val isTracking: Boolean = false

)