package com.paperless.app.action.ui

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paperless.app.Screens
import com.paperless.app.datamodel.TransactionSummary
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.ui.theme.*
import com.paperless.app.viewmodel.DashboardViewModel
import com.paperless.app.widget.DashboardActionButton
import com.paperless.app.widget.DashboardTile
import com.paperless.app.widget.LocalImage
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
        is NetworkResponse.Completed -> PaperlessDashboard(
            transactionSummary = dashboardData.data,
            navHostController
        )
        else -> Timber.d("initial State")
    }

}

@Composable
fun PaperlessDashboard(
    transactionSummary: TransactionSummary,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.paperless_font.h4,
            color =
            MaterialTheme.colors.Paperless_Text_Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            DashboardTile(
                bgColor = MaterialTheme.colors.Paperless_White,
                title = "Total Income",
                amount = transactionSummary.totalIncome
            )
            DashboardTile(
                bgColor = MaterialTheme.colors.Paperless_Card,
                title = "Total Expense",
                amount = transactionSummary.totalExpense
            )
            DashboardTile(
                bgColor = MaterialTheme.colors.Paperless_White,
                title = "Monthly Expense",
                amount = transactionSummary.monthlyExpense
            )
            Spacer(modifier = Modifier.size(8.dp))
        }

        Spacer(modifier = Modifier.size(64.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            DashboardActionButton(
                iconId = com.paperless.app.R.drawable.budget_icon,
                text = "Budget",
                color = MaterialTheme.colors.Paperless_Card
            ) {
                navHostController.navigate(Screens.BudgetSummary.name)
            }
            Spacer(modifier = Modifier.size(4.dp))
            DashboardActionButton(
                iconId = com.paperless.app.R.drawable.reminder,
                text = "Reminder",
                color = MaterialTheme.colors.Paperless_White
            ) {}
            Spacer(modifier = Modifier.size(4.dp))
            DashboardActionButton(
                iconId = com.paperless.app.R.drawable.goal_icon,
                text = "My Goal",
                color = MaterialTheme.colors.Paperless_White
            ) {
                navHostController.navigate(Screens.GoalSummary.name)
            }
            Spacer(modifier = Modifier.size(8.dp))
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth().
            padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.paperless_font.h4,
                color =
                MaterialTheme.colors.Paperless_Text_Black
            )
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(40.dp)
                    .background(
                        color = MaterialTheme.colors.Paperless_Light_Card_2,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                LocalImage(
                    imageId = com.paperless.app.R.drawable.more_icon,
                    contentDes = "more transaction",
                    color = MaterialTheme.colors.Paperless_Button
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Column(modifier = Modifier.padding(16.dp)) {
            transactionSummary.lastFiveTransaction.forEach {
                TransactionCard(transaction = it)
                Spacer(modifier = Modifier.size(24.dp))
            }
        }

    }
}

fun getCurrentMonthYear(): String {
    val calendar = Calendar.getInstance()
    return "${calendar.get(Calendar.MONTH)}_${calendar.get(Calendar.YEAR)}"
}