package madproject.deepaks3533898.fitnessstepscounter.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_intake")
data class WaterEntity(

    @PrimaryKey
    val date: String,

    val glasses: Int

)