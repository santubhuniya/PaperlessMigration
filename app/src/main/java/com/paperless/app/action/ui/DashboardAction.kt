package com.paperless.app.action.ui

import android.widget.Space
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paperless.app.datamodel.TransactionSummary
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.ui.theme.*
import com.paperless.app.viewmodel.DashboardViewModel
import com.paperless.app.widget.DashboardActionButton
import com.paperless.app.widget.DashboardTile
import com.paperless.app.widget.TransactionCard
import timber.log.Timber
import java.lang.Error
import java.util.*


@Composable
fun DashboardUi(navHostController: NavHostController) {
    val dashboardViewModel: DashboardViewModel = hiltViewModel()

    //get dashboard for paperless
    LaunchedEffect(Unit) {
        dashboardViewModel.getPaperlessDashboard(3, getCurrentMonthYear())
    }
    val dashboardData = dashboardViewModel.dashboardData.value

    when (dashboardData) {
        is NetworkResponse.Loading -> Timber.d("Loading")
        is NetworkResponse.Error -> Timber.d("Error")
        is NetworkResponse.Completed -> PaperlessDashboard(transactionSummary = dashboardData.data)
        else -> Timber.d("initial State")
    }

}

@Composable
fun PaperlessDashboard(transactionSummary: TransactionSummary) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.h5,
            color =
            MaterialTheme.colors.Paperless_Text_Black
        )
        Spacer(modifier = Modifier.size(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            DashboardTile(
                bgColor = MaterialTheme.colors.Paperless_White,
                title = "Total Income",
                amount = transactionSummary.totalIncome
            )
            Spacer(modifier = Modifier.padding(4.dp))
            DashboardTile(
                bgColor = MaterialTheme.colors.Paperless_Card,
                title = "Total Expense",
                amount = transactionSummary.totalExpense
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            DashboardActionButton(
                iconId = com.paperless.app.R.drawable.budget_icon,
                text = "Budget",
                color = MaterialTheme.colors.Paperless_White
            )
            Spacer(modifier = Modifier.size(8.dp))
            DashboardActionButton(
                iconId = com.paperless.app.R.drawable.reminder,
                text = "Reminder",
                color = MaterialTheme.colors.Paperless_Card
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Recent Transactions",
            style = MaterialTheme.typography.h5,
            color =
            MaterialTheme.colors.Paperless_Text_Black
        )

        Spacer(modifier = Modifier.size(16.dp))
        Column() {


            transactionSummary.lastFiveTransaction.forEach {
                TransactionCard(transaction = it)
                Spacer(modifier = Modifier.size(16.dp))
            }
        }

    }
}

fun getCurrentMonthYear(): String {
    val calendar = Calendar.getInstance()
    return "${calendar.get(Calendar.MONTH)}_${calendar.get(Calendar.YEAR)}"
}