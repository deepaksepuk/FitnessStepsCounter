package madproject.deepaks3533898.fitnessstepscounter.model


data class StepTrackerState(

    val isTracking: Boolean = false,

    val steps: Int = 0,

    val distanceKm: Float = 0f,

    val calories: Float = 0f,

    val durationSeconds: Long = 0L

)