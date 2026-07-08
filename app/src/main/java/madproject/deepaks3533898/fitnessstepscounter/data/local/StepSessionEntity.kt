package madproject.deepaks3533898.fitnessstepscounter.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "step_sessions")
data class StepSessionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val date: String,

    val steps: Int,

    val distance: Float,

    val calories: Float,

    val duration: Long

)

@Entity(tableName = "goal_settings")
data class GoalEntity(

    @PrimaryKey
    val id: Int = 1,

    val dailyGoal: Int,
    val dailyCalorieGoal: Int

)