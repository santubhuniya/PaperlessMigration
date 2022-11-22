package com.paperless.app.widget.chart

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


@Composable
fun PieChartCard(pieDataList : List<PieChartData>){

    AndroidView(
        modifier = Modifier.fillMaxWidth().height(180.dp),
        factory = {context->
        val pieChart = PieChart(context)
        val pieEntry = pieDataList.map { data->
            PieEntry(
                data.data,
                data.label
            )
        }
        val pieSeries = PieDataSet(pieEntry,"PieChart").apply {
            colors = listOf(
                Color.RED,
                Color.YELLOW,
                Color.BLUE
            )
        }
        pieChart.apply {
            data = PieData(pieSeries)
            maxAngle = 180F
            holeRadius = 15f
            transparentCircleRadius = 25f
            setHoleColor(Color.WHITE)
            rotationAngle = -180F
        }
        pieChart.invalidate()
        pieChart
    })
}

data class PieChartData(val data : Float,val label : String)