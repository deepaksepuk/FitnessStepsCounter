package madproject.deepaks3533898.fitnessstepscounter.ui

import androidx.compose.ui.unit.dp
import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

enum class ChartType {
    LINE,
    BAR
}

@Composable
fun FitnessChart(

    labels: List<String>,

    values: List<Int>,

    chartType: ChartType

) {

    if (chartType == ChartType.LINE) {

        AndroidView(

            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),

            factory = { context ->

                LineChart(context).apply {

                    description.isEnabled = false

                    legend.isEnabled = false

                    setTouchEnabled(true)

                    setPinchZoom(true)

                    animateX(1000)

                    axisRight.isEnabled = false

                    xAxis.position =
                        XAxis.XAxisPosition.BOTTOM

                    xAxis.granularity = 1f

                    xAxis.valueFormatter =
                        IndexAxisValueFormatter(labels)

                    axisLeft.axisMinimum = 0f

                }

            },

            update = { chart ->

                val entries =
                    values.mapIndexed { index, value ->

                        Entry(

                            index.toFloat(),

                            value.toFloat()

                        )

                    }

                val dataSet =
                    LineDataSet(

                        entries,

                        "Steps"

                    )

                dataSet.color =
                    Color.parseColor("#4CAF50")

                dataSet.lineWidth = 3f

                dataSet.circleRadius = 5f

                dataSet.setCircleColor(
                    Color.parseColor("#4CAF50")
                )

                dataSet.valueTextSize = 11f

                dataSet.mode =
                    LineDataSet.Mode.CUBIC_BEZIER

                chart.data =
                    LineData(dataSet as ILineDataSet?)

                chart.invalidate()

            }

        )

    } else {

        AndroidView(

            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),

            factory = { context ->

                BarChart(context).apply {

                    description.isEnabled = false

                    legend.isEnabled = false

                    animateY(1000)

                    axisRight.isEnabled = false

                    xAxis.position =
                        XAxis.XAxisPosition.BOTTOM

                    xAxis.granularity = 1f

                    xAxis.valueFormatter =
                        IndexAxisValueFormatter(labels)

                    axisLeft.axisMinimum = 0f

                }

            },

            update = { chart ->

                val entries =
                    values.mapIndexed { index, value ->

                        BarEntry(

                            index.toFloat(),

                            value.toFloat()

                        )

                    }

                val dataSet =
                    BarDataSet(

                        entries,

                        "Steps"

                    )

                dataSet.color =
                    Color.parseColor("#2196F3")

                dataSet.valueTextSize = 11f

                chart.data =
                    BarData(dataSet)

                chart.invalidate()

            }

        )

    }

}