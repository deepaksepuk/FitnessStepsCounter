package madproject.deepaks3533898.fitnessstepscounter.data.repository

import kotlinx.coroutines.flow.Flow
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

    fun getLatestSession(): Flow<StepSessionEntity?> {
        return dao.getLatestSession()
    }

    fun getAllSessions():

            Flow<List<StepSessionEntity>> {

        return dao.getAllSessions()

    }

}