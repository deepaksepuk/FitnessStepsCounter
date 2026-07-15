package madproject.deepaks3533898.fitnessstepscounter.data.repository

import kotlinx.coroutines.flow.Flow
import madproject.deepaks3533898.fitnessstepscounter.data.local.GoalEntity
import madproject.deepaks3533898.fitnessstepscounter.data.local.StepDao
import madproject.deepaks3533898.fitnessstepscounter.data.local.StepSessionEntity

class StepRepository(

    private val dao: StepDao

) {

    suspend fun insertSession(

        session: StepSessionEntity

    ) {

        dao.insertSession(session)

    }

    suspend fun getSessionByDate(date: String): StepSessionEntity? {
        return dao.getSessionByDate(date)
    }

    suspend fun updateSession(session: StepSessionEntity) {
        dao.updateSession(session)
    }

    fun getLatestSession(): Flow<StepSessionEntity?> {
        return dao.getLatestSession()
    }

    fun getAllSessions():

            Flow<List<StepSessionEntity>> {

        return dao.getAllSessions()

    }

    suspend fun saveGoal(goal: GoalEntity) {
        dao.saveGoal(goal)
    }

    fun getGoal(): Flow<GoalEntity?> {
        return dao.getGoal()
    }

    suspend fun insertSessions(

        sessions: List<StepSessionEntity>

    ) {

        sessions.forEach {

            dao.insertSession(it)

        }

    }

//    fun getAllSessions() = dao.getAllSessions()

}