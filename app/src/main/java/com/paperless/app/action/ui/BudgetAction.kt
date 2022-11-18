package com.paperless.app.action.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paperless.app.datamodel.BudgetSummary
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.viewmodel.BudgetViewModel
import com.paperless.app.widget.GenericBudgetRow
import timber.log.Timber


@Composable
fun BudgetSummaryAction(navHostController: NavHostController){

    val budgetViewModel : BudgetViewModel = hiltViewModel()
    val expenseBudgetList = budgetViewModel.budgetExpenseList.value

    LaunchedEffect(Unit){
        budgetViewModel.getBudgetDetails(3, getCurrentMonthYear())
    }

    when(expenseBudgetList){
        is NetworkResponse.Loading -> Timber.d("Loading")
        is NetworkResponse.Error -> Timber.d("Error")
        is NetworkResponse.Completed -> ExpenseBudgetSummaryCard(budgetList = expenseBudgetList.data)
        else -> Timber.d("initial State")
    }
}

@Composable
fun ExpenseBudgetSummaryCard(budgetList : List<BudgetSummary>){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        budgetList.forEach {
            GenericBudgetRow(budgetName = it.expenseTypeName ?: "",
                currentAmount = it.expenseAmount,
                targetAmount = it.budgetAmount
            )
            Spacer(modifier = Modifier.size(24.dp))
        }
    }

}