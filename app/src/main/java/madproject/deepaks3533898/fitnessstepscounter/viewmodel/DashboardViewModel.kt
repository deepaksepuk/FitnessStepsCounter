package madproject.deepaks3533898.fitnessstepscounter.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import madproject.deepaks3533898.fitnessstepscounter.data.local.DatabaseProvider
import madproject.deepaks3533898.fitnessstepscounter.data.local.GoalUiState
import madproject.deepaks3533898.fitnessstepscounter.data.local.StepSessionEntity
import madproject.deepaks3533898.fitnessstepscounter.data.local.WaterEntity
import madproject.deepaks3533898.fitnessstepscounter.data.repository.StepRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = StepRepository(
        DatabaseProvider
            .getDatabase(application)
            .stepDao()
    )

    private val _latestSession =
        MutableStateFlow<StepSessionEntity?>(null)

    val latestSession: StateFlow<StepSessionEntity?> =
        _latestSession.asStateFlow()

    private val _goal = MutableStateFlow(GoalUiState())

    val goal: StateFlow<GoalUiState> =
        _goal.asStateFlow()

    private val today = SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.getDefault()
    ).format(Date())


    private val _water =
        MutableStateFlow(0)

    val water: StateFlow<Int> =
        _water.asStateFlow()

    init {

        viewModelScope.launch {

            repository
                .getLatestSession()
                .collect {

                    _latestSession.value = it

                }


            repository.getGoal().collect { goal ->

                if (goal != null) {

                    _goal.value = GoalUiState(

                        stepGoal = goal.dailyGoal,

                        calorieGoal = goal.dailyCalorieGoal

                    )

                }

            }

        }

        observeWater()


    }

    private fun observeWater() {

        viewModelScope.launch {

            repository.getWaterByDate(today).collect { water ->

                _water.value =
                    water?.glasses ?: 0

            }

        }

    }

    fun addWater() {

        if (_water.value >= 8)
            return

        viewModelScope.launch {

            val newCount =
                _water.value + 1

            repository.saveWater(

                WaterEntity(

                    date = today,

                    glasses = newCount

                )

            )

        }

    }

}