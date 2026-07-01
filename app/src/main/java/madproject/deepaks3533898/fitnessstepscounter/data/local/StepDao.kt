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

    @Query("DELETE FROM step_sessions")
    suspend fun deleteAll()

}