package com.paperless.app.action.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paperless.app.Screens
import com.paperless.app.datamodel.ChartRequest
import com.paperless.app.datamodel.ChartSummary
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.ui.theme.Paperless_Text_Black
import com.paperless.app.ui.theme.Paperless_Text_Grey
import com.paperless.app.ui.theme.paperless_font
import com.paperless.app.viewmodel.NavigationViewModel
import com.paperless.app.viewmodel.TransactionViewModel
import com.paperless.app.widget.TabBarHeader
import com.paperless.app.widget.chart.BarChartCard
import com.paperless.app.widget.chart.BarChartData
import com.paperless.app.widget.chart.PieChartCard
import com.paperless.app.widget.chart.PieChartData
import kotlinx.coroutines.yield
import timber.log.Timber
import java.util.*

@Composable
fun StatisticsAction(
    navHostController: NavHostController,
    navigationViewModel: NavigationViewModel
) {
    val transactionViewModel: TransactionViewModel = hiltViewModel()
    val statisticsResponse = transactionViewModel.statisticsResponse.value
    LaunchedEffect(Unit) {
        getStatisticalData(ChartType.weekly, transactionViewModel)
        navigationViewModel.setupHeaderAndFooter(Screens.Statistics)
    }
    when (statisticsResponse) {
        is NetworkResponse.Loading -> Timber.d("Loading")
        is NetworkResponse.Error -> Timber.d("Error")
        is NetworkResponse.Completed -> StatisticsCard(
            chartSummary = statisticsResponse.data,
            transactionViewModel
        )
        else -> Timber.d("Initial Response")
    }
}

@Composable
fun StatisticsCard(chartSummary: ChartSummary, transactionViewModel: TransactionViewModel) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        TabBarHeader(
            listOf(
                ChartType.weekly.name,
                ChartType.monthly.name,
                ChartType.yearly.name
            ),
            transactionViewModel.selectedChartType.value
        ) { selectedType ->
            Timber.d("Selected - $selectedType")
            transactionViewModel.selectedChartType.value = selectedType
            when (selectedType.lowercase(Locale.getDefault())) {
                ChartType.weekly.name -> {
                    getStatisticalData(ChartType.weekly, transactionViewModel)
                }
                ChartType.monthly.name -> {
                    getStatisticalData(ChartType.monthly, transactionViewModel)
                }
                ChartType.yearly.name -> {
                    getStatisticalData(ChartType.yearly, transactionViewModel)

                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(8.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(8.dp)
        ) {

            val barChartData = mutableListOf<BarChartData>()
            var index = 0
            chartSummary.chartDataList?.map {
                barChartData.add(
                    BarChartData(
                        index = index,
                        xData = it.xData,
                        yIncome = it.yIncome,
                        yExpese = it.yExpense,
                        yBudget = it.yBudget
                    )
                )
                index++
            }
            BarChartCard(
                barChartData = barChartData,
                transactionViewModel.selectedChartType.value == ChartType.yearly.name
            )

        }
        Spacer(modifier = Modifier.size(32.dp))

        Text("Expense by category",
            style = MaterialTheme.typography.paperless_font.body1,
            color =
            MaterialTheme.colors.Paperless_Text_Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        PieChartCard(
            pieDataList = chartSummary.categoryList?.map {
                PieChartData(
                    label = it.categoryName,
                    data = it.amount
                )
            } ?: listOf()
        )
        Spacer(modifier = Modifier.size(64.dp))
    }
}

fun getStatisticalData(
    chartType: ChartType,
    transactionViewModel: TransactionViewModel
) {


    transactionViewModel.getStatisticsData(
        ChartRequest(
            month = 10,
            year = 2022,
            chartType = chartType.name,
            userId = 3,
            week = 0
        )
    )

}

enum class ChartType {
    weekly,
    monthly,
    yearly
}