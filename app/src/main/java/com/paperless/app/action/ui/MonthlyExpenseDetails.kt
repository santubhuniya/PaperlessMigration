package com.paperless.app.action.ui

import androidx.compose.foundation.layout.*
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
import com.paperless.app.datamodel.MonthlyExpenseDetails
import com.paperless.app.datamodel.Transaction
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.ui.theme.Paperless_Text_Black
import com.paperless.app.ui.theme.paperless_font
import com.paperless.app.viewmodel.TransactionViewModel
import com.paperless.app.widget.PaperlessAmountDisplay
import com.paperless.app.widget.TransactionCard
import timber.log.Timber

@Composable
fun MonthlyExpenseDetails(
    navHostController: NavHostController
) {
    val transactionViewModel: TransactionViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        transactionViewModel.getMonthlyExpenseDetails()
    }
    val monthlyExpenseDetails = transactionViewModel.monthlyTranscationDetails.value

    when (monthlyExpenseDetails) {
        is NetworkResponse.Loading -> Timber.d("Loading")
        is NetworkResponse.Error -> Timber.d("Error")
        is NetworkResponse.InitialState -> Timber.d("Initial State")
        is NetworkResponse.Completed -> MonthlyExpenseCardDetails(monthlyExpenseDetails.data)
    }
}

@Composable
fun MonthlyExpenseCardDetails(monthlyExpenseDetails: MonthlyExpenseDetails) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PaperlessAmountDisplay(monthlyExpenseDetails.totalExpense)
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "You spent more than your budget for this month",
            style = MaterialTheme.typography.paperless_font.body1,
            color =
            MaterialTheme.colors.Paperless_Text_Black,
            fontWeight = FontWeight.SemiBold

        )
        Spacer(modifier = Modifier.size(24.dp))
        // budget category

        //top spending

        Column(modifier = Modifier.padding(16.dp)) {
            monthlyExpenseDetails.listTopSpending?.forEach {
                TransactionCard(transaction = Transaction(
                    it.expenseId,
                    it.expenseName,
                    it.amount,
                    it.categoryName,
                    isExpense = true,
                    txnDate = it.date
                ))
                Spacer(modifier = Modifier.size(24.dp))
            }
        }
    }
}