package madproject.deepaks3533898.fitnessstepscounter.viewmodel

import madproject.deepaks3533898.fitnessstepscounter.data.local.GoalUiState



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import madproject.deepaks3533898.fitnessstepscounter.data.local.DatabaseProvider
import madproject.deepaks3533898.fitnessstepscounter.data.local.GoalEntity
import madproject.deepaks3533898.fitnessstepscounter.data.repository.StepRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


sealed class UiEvent {

    data class ShowSnackbar(
        val message: String
    ) : UiEvent()

}

class GoalViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = StepRepository(
        DatabaseProvider
            .getDatabase(application)
            .stepDao()
    )

    private val _uiState = MutableStateFlow(
        GoalUiState()
    )

    val uiState: StateFlow<GoalUiState> =
        _uiState.asStateFlow()


    private val _events = Channel<UiEvent>()

    val events = _events.receiveAsFlow()


    init {

        viewModelScope.launch {

            repository.getGoal().collect { goal ->

                if (goal != null) {

                    _uiState.value = GoalUiState(

                        stepGoal = goal.dailyGoal,

                        calorieGoal = goal.dailyCalorieGoal

                    )

                }

            }

        }

    }

    fun saveGoals(

        stepGoal: Int,

        calorieGoal: Int

    ) {

        viewModelScope.launch {

            repository.saveGoal(

                GoalEntity(

                    dailyGoal = stepGoal,

                    dailyCalorieGoal = calorieGoal

                )

            )

            _events.send(

                UiEvent.ShowSnackbar(

                    "Goals updated successfully."

                )

            )

        }

    }

}