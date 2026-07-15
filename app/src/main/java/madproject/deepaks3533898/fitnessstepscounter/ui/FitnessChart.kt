package madproject.deepaks3533898.fitnessstepscounter.ui

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    key(chartType, labels) {

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

                        axisRight.isEnabled = false

                        axisLeft.axisMinimum = 0f

                        xAxis.position =
                            XAxis.XAxisPosition.BOTTOM

                        xAxis.granularity = 1f

                        xAxis.isGranularityEnabled = true

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
                        ).apply {

                            color =
                                Color.parseColor("#4CAF50")

                            lineWidth = 3f

                            circleRadius = 5f

                            setCircleColor(
                                Color.parseColor("#4CAF50")
                            )

                            valueTextSize = 11f

                            mode =
                                LineDataSet.Mode.CUBIC_BEZIER

                        }

                    chart.xAxis.apply {

                        valueFormatter =
                            IndexAxisValueFormatter(labels)

                        labelCount = labels.size

                        granularity = 1f

                        isGranularityEnabled = true

                        setAvoidFirstLastClipping(true)

                    }

                    chart.data =
                        LineData(dataSet)

                    chart.notifyDataSetChanged()

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

                        axisRight.isEnabled = false

                        axisLeft.axisMinimum = 0f

                        xAxis.position =
                            XAxis.XAxisPosition.BOTTOM

                        xAxis.granularity = 1f

                        xAxis.isGranularityEnabled = true

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
                        ).apply {

                            color =
                                Color.parseColor("#2196F3")

                            valueTextSize = 11f

                        }

                    chart.xAxis.apply {

                        valueFormatter =
                            IndexAxisValueFormatter(labels)

                        labelCount = labels.size

                        granularity = 1f

                        isGranularityEnabled = true

                        setAvoidFirstLastClipping(true)

                    }

                    chart.data =
                        BarData(dataSet)

                    chart.notifyDataSetChanged()

                    chart.invalidate()

                }

            )

        }

    }

}