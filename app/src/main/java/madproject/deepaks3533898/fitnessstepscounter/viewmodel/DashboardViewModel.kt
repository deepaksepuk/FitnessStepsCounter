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
import madproject.deepaks3533898.fitnessstepscounter.data.repository.StepRepository

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




    }

}