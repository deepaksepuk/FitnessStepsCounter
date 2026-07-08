package madproject.deepaks3533898.fitnessstepscounter.data.local


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(

    entities = [

        StepSessionEntity::class,
                GoalEntity::class


    ],

    version = 1,
    exportSchema = false
)

abstract class StepDatabase : RoomDatabase() {

    abstract fun stepDao(): StepDao

}