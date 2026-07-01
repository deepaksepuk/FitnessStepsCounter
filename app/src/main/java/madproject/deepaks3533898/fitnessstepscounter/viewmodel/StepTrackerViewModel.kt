package madproject.deepaks3533898.fitnessstepscounter.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import madproject.deepaks3533898.fitnessstepscounter.data.local.DatabaseProvider
import madproject.deepaks3533898.fitnessstepscounter.data.local.StepSessionEntity
import madproject.deepaks3533898.fitnessstepscounter.data.repository.StepRepository
import madproject.deepaks3533898.fitnessstepscounter.model.StepTrackerState
import madproject.deepaks3533898.fitnessstepscounter.sensor.StepSensorManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class StepTrackerViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val sensorManager = StepSensorManager(application)

    private val _uiState = MutableStateFlow(StepTrackerState())

    val uiState: StateFlow<StepTrackerState> =
        _uiState.asStateFlow()

    private var timerJob: Job? = null

    private var initialSensorValue: Int? = null

    private var pausedSteps = 0

    private val repository = StepRepository(

        DatabaseProvider

            .getDatabase(application)

            .stepDao()

    )

    private fun saveSession() {

        viewModelScope.launch {

            val formatter = SimpleDateFormat(

                "dd/MM/yyyy",

                Locale.getDefault()

            )

            val session = StepSessionEntity(

                date = formatter.format(Date()),

                steps = _uiState.value.steps,

                distance = _uiState.value.distanceKm,

                calories = _uiState.value.calories,

                duration = _uiState.value.durationSeconds

            )

            repository.insertSession(session)

        }

    }

    fun startTracking() {

        if (_uiState.value.isTracking)
            return

        _uiState.value = _uiState.value.copy(
            isTracking = true
        )

        sensorManager.startTracking { totalSteps ->

            if (initialSensorValue == null) {

                initialSensorValue = totalSteps

            }

            val sessionSteps =
                (totalSteps - initialSensorValue!!) + pausedSteps

            calculateFitness(sessionSteps)

        }

        startTimer()

    }


    fun pauseTracking() {

        sensorManager.stopTracking()

        timerJob?.cancel()

        _uiState.value = _uiState.value.copy(
            isTracking = false
        )

    }

    fun resumeTracking() {

        if (_uiState.value.isTracking)
            return

        pausedSteps = _uiState.value.steps

        initialSensorValue = null

        _uiState.value = _uiState.value.copy(
            isTracking = true
        )

        sensorManager.startTracking { totalSteps ->

            if (initialSensorValue == null) {

                initialSensorValue = totalSteps

            }

            val sessionSteps =
                (totalSteps - initialSensorValue!!) + pausedSteps

            calculateFitness(sessionSteps)

        }

        startTimer()

    }


    fun stopTracking() {

        sensorManager.stopTracking()

        timerJob?.cancel()

        pausedSteps = 0

        initialSensorValue = null

        if (_uiState.value.steps > 0) {

            saveSession()

        }

        _uiState.value = StepTrackerState()

    }

    private fun calculateFitness(
        steps: Int
    ) {

        val distance = steps * 0.0008f

        val calories = steps * 0.04f

        _uiState.value = _uiState.value.copy(

            steps = steps,

            distanceKm = distance,

            calories = calories

        )

    }


    private fun startTimer() {

        timerJob?.cancel()

        timerJob = viewModelScope.launch {

            while (true) {

                delay(1000)

                val currentDuration =
                    _uiState.value.durationSeconds + 1

                _uiState.value = _uiState.value.copy(
                    durationSeconds = currentDuration
                )

            }

        }

    }

    fun isSensorAvailable(): Boolean {

        return sensorManager.isStepCounterAvailable()

    }

}