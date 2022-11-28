package com.paperless.app.widget.chart

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@Composable
fun HalfPieChartCard(pieDataList : List<PieChartData>){

    AndroidView(
        modifier = Modifier.fillMaxWidth().height(280.dp),
        factory = {context->
        val pieChart = PieChart(context)
        val pieEntry = pieDataList.map { data->
            PieEntry(
                data.data,
                data.label
            )
        }
        val pieSeries = PieDataSet(pieEntry,"").apply {
            colors = listOf(
                Color(0xFFFF9E9E).toArgb(),
                Color(0xFF3C3DBF).toArgb(),
                Color(0xFF7FC4DD).toArgb()
            )
            valueTextSize = 15f
            setValueTextColors(listOf(Color.White.toArgb()))
            valueFormatter = object : ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    return "${value.roundToInt()}%"
                }
            }
        }
        pieChart.apply {
            data = PieData(pieSeries)
            maxAngle = 180F
            holeRadius = 15f
            transparentCircleRadius = 25f
            setHoleColor(Color.White.toArgb())
            rotationAngle = -180F
            legend.yOffset = 50f
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
            description.isEnabled = false
            setEntryLabelTextSize(8f)
            setCenterTextColor(Color.White.toArgb())
            setUsePercentValues(true)
        }
        pieChart.invalidate()
        pieChart
    })
}

data class PieChartData(val data : Float,val label : String)
@Composable
fun PieChartCard(pieDataList : List<PieChartData>){

    AndroidView(
        modifier = Modifier.fillMaxWidth().height(250.dp),
        factory = {context->
            val pieChart = PieChart(context)
            val pieEntry = pieDataList.map { data->
                PieEntry(
                    data.data,
                    data.label
                )
            }
            val pieSeries = PieDataSet(pieEntry,"").apply {
                colors = listOf(
                    Color(0xFFFF9E9E).toArgb(),
                    Color(0xFF3C3DBF).toArgb(),
                    Color(0xFF7FC4DD).toArgb()
                )
                valueTextSize = 15f
                setValueTextColors(listOf(Color.White.toArgb()))
                valueFormatter = object : ValueFormatter(){
                    override fun getFormattedValue(value: Float): String {
                        return "${value.roundToInt()}%"
                    }
                }
            }
            pieChart.apply {
                data = PieData(pieSeries)
                holeRadius = 15f
                transparentCircleRadius = 25f
                setHoleColor(Color.White.toArgb())
                legend.xOffset = 16f
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                description.isEnabled = false
                setEntryLabelTextSize(8f)
                setCenterTextColor(Color.White.toArgb())
                setUsePercentValues(true)

            }
            pieChart.invalidate()
            pieChart
        })
}

