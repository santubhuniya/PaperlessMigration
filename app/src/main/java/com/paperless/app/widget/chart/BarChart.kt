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
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.paperless.app.ui.theme.Paperless_Card

@Composable
fun BarChartCard(barChartData: List<BarChartData>){
    AndroidView(
        modifier = Modifier.fillMaxWidth()
            .height(180.dp),
        factory = { context->
        val barChart = BarChart(context)
        val entrySet =barChartData.map { barData->
            BarEntry(
              barData.xData,
              barData.yData
            )
        }
        val barDataSet = BarDataSet(
            entrySet, "Monthly Expense"
        ).apply {
            color = Color.Yellow.toArgb()
        }
        barChart.data = BarData(barDataSet)
            barChart.invalidate()
        barChart
    })
}

data class BarChartData (val xData : Float, val yData : Float)