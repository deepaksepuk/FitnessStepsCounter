package madproject.deepaks3533898.fitnessstepscounter.sensor


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager


class StepSensorManager(
    context: Context
) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val stepSensor =
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    private var callback: ((Int) -> Unit)? = null

    fun startTracking(
        onStepChanged: (Int) -> Unit
    ) {

        callback = onStepChanged

        stepSensor?.let {

            sensorManager.registerListener(

                this,

                it,

                SensorManager.SENSOR_DELAY_UI

            )

        }

    }

    fun stopTracking() {

        sensorManager.unregisterListener(this)

    }

    override fun onSensorChanged(event: SensorEvent?) {

        event ?: return

        val totalSteps = event.values[0].toInt()

        callback?.invoke(totalSteps)

    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) {
    }

    fun isStepCounterAvailable(): Boolean {

        return stepSensor != null

    }

}