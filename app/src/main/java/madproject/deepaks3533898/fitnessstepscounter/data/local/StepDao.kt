package madproject.deepaks3533898.fitnessstepscounter.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(
        session: StepSessionEntity
    )

    @Query("SELECT * FROM step_sessions ORDER BY id DESC")
    fun getAllSessions(): Flow<List<StepSessionEntity>>

    @Query("SELECT * FROM step_sessions ORDER BY id DESC LIMIT 1")
    fun getLatestSession(): Flow<StepSessionEntity?>

    @Query("DELETE FROM step_sessions")
    suspend fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGoal(goal: GoalEntity)

    @Query("SELECT * FROM goal_settings WHERE id = 1")
    fun getGoal(): Flow<GoalEntity?>

}