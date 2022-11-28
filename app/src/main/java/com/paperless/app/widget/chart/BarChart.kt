package com.paperless.app.widget.chart

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.paperless.app.ui.theme.Paperless_Card

@Composable
fun BarChartCard(barChartData: List<BarChartData>, forceLabelCount: Boolean = false) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        factory = { context ->
            val barChart = BarChart(context)
            val xValueFormatter = object : ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    if(value == 0.0f) return "" else return "$value"
                }
            }
            val barDataSetExpense = BarDataSet(
                barChartData.map { barData ->
                    BarEntry(
                        barData.index.toFloat(),
                        barData.yExpese
                    )
                }, "Expense"
            ).apply {
                color = Color(0xFFFF9E9E).toArgb()
                valueFormatter = xValueFormatter
            }
            val barDataIncome = BarDataSet(
                barChartData.map { barData ->
                    BarEntry(
                        barData.index.toFloat(),
                        barData.yIncome
                    )
                }, "Income"
            ).apply {
                color = Color(0xFF3C3DBF).toArgb()
                valueFormatter = xValueFormatter
            }
            val barDataBudget = BarDataSet(
                barChartData.map { barData ->
                    BarEntry(
                        barData.index.toFloat(),
                        barData.yBudget
                    )
                }, "Budget"
            ).apply {
                color = Color(0xFF7FC4DD).toArgb()
                valueFormatter = xValueFormatter
            }
            barChart.data = BarData(
                barDataSetExpense,
                barDataIncome,
                //barDataBudget
            ).apply {
                barWidth = .4f //.265f // bar width = (1- groupSpace)/nbr of group
                groupBars(0f,0.2f,0f)
            }
            //FF9E9E
            barChart.setDrawGridBackground(false)
            barChart.setDrawValueAboveBar(false)
            barChart.setFitBars(false)
            barChart.description.isEnabled = false
            barChart.axisRight.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                isEnabled = false
            }
            barChart.axisLeft.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
            }
            barChart.xAxis.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                if (forceLabelCount) {
                    setLabelCount(12, false)
                }
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setCenterAxisLabels(true)
                isGranularityEnabled = true
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return barChartData.findLast {
                            it.index == value.toInt()
                        }?.xData ?: ""
                    }
                }
                mAxisMinimum = -.5f
                mAxisMaximum = barChartData.size + .5f
            }
            barChart.legend.yOffset = 8f
            barChart.setExtraOffsets(5f, 5f, 5f, 15f)
            barChart.invalidate()
            barChart.setDrawValueAboveBar(true)
            barChart.setFitBars(true)
            barChart
        })
}

data class BarChartData(
    val index: Int,
    val xData: String,
    val yIncome: Float,
    val yExpese: Float,
    val yBudget: Float
)