package madproject.deepaks3533898.fitnessstepscounter.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: StepDatabase? = null

    fun getDatabase(
        context: Context
    ): StepDatabase {

        return INSTANCE ?: synchronized(this) {

            val database = Room.databaseBuilder(

                context,

                StepDatabase::class.java,

                "fitness_database"

            ).build()

            INSTANCE = database

            database

        }

    }

}