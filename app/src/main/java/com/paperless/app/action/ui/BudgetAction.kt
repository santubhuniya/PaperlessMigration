package com.paperless.app.action.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paperless.app.datamodel.BudgetSummary
import com.paperless.app.datamodel.NewBudgetRequest
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.viewmodel.BudgetViewModel
import com.paperless.app.widget.GenericBudgetRow
import com.paperless.app.widget.PageHeader
import com.paperless.app.widget.PaperlessAmountDisplay
import com.paperless.app.widget.format2Decimal
import timber.log.Timber
import java.util.*


@Composable
fun BudgetSummaryAction(navHostController: NavHostController) {

    val budgetViewModel: BudgetViewModel = hiltViewModel()
    val expenseBudgetList = budgetViewModel.budgetExpenseList.value

    LaunchedEffect(Unit) {
        budgetViewModel.getBudgetDetails(3, getCurrentMonthYear())
    }


    when (expenseBudgetList) {
        is NetworkResponse.Loading -> Timber.d("Loading")
        is NetworkResponse.Error -> Timber.d("Error")
        is NetworkResponse.Completed -> ExpenseBudgetSummaryCard(
            budgetList = expenseBudgetList.data,
            budgetViewModel
        ){
            budgetViewModel.getBudgetDetails(3, getCurrentMonthYear())
        }
        else -> Timber.d("initial State")
    }
}

@Composable
fun ExpenseBudgetSummaryCard(
    budgetList: List<BudgetSummary>,
    budgetViewModel: BudgetViewModel,
    doRefresh :()->Unit
) {
    val totalBudgetAmount = remember {
        mutableStateOf(0.00F)
    }
    LaunchedEffect(Unit) {
        var budgetAmount = 0.0F
        budgetList.map {
            it.budgetAmount?.let { amount ->
                budgetAmount += amount
            }
        }
        totalBudgetAmount.value = budgetAmount
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp, vertical = 16.dp
            )
    ) {
        PageHeader("Monthly Budget")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 0.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PaperlessAmountDisplay(totalBudgetAmount.value.format2Decimal())
            Spacer(modifier = Modifier.size(32.dp))
            budgetList.forEach { budget ->
                BudgetRow(budget,budgetViewModel){
                    totalBudgetAmount.value = it
                }
                Spacer(modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.size(64.dp))
        }
    }

}

@Composable
fun BudgetRow(budget : BudgetSummary,budgetViewModel : BudgetViewModel, updateTotal : (Float)->Unit){
    val showKeyBoard = remember{
        mutableStateOf(false)
    }
    val budgetAmount = remember{
        mutableStateOf(budget.budgetAmount)
    }

    GenericBudgetRow(
        budgetName = budget.expenseTypeName,
        currentAmount = budget.expenseAmount,
        targetAmount = budgetAmount,
        showKeyboard = showKeyBoard
    ) { amount ->
        budgetViewModel.addUpdateBudget(
            NewBudgetRequest(
                budgetSeq = budget.budgetId,
                userId = 3,
                budgetFor = budget.monthYear,
                budgetTitle = "Budget for ${budget.expenseTypeName}",
                budgetAmount = amount.toFloat(),
                dateAdded = Date().time,
                transactionType = budget.expenseTypeId,
                transactionTypeLevel = 0
            )
        )
    }
    val budgetUpdateResponse = budgetViewModel.newBudgetResp.value
    when (budgetUpdateResponse) {
        is NetworkResponse.Loading -> Timber.d("Loading")
        is NetworkResponse.Error -> Timber.d("Error")
        is NetworkResponse.Completed -> {
            Timber.d("Budget Id - ${budgetUpdateResponse.data}")
            //budgetViewModel.getBudgetDetails(3, getCurrentMonthYear())
            showKeyBoard.value = false
            if(budget.budgetId == budgetUpdateResponse.data.budgetSeq) {
                budgetAmount.value = budgetUpdateResponse.data.newAmount
                updateTotal.invoke(budgetUpdateResponse.data.totalBudget)
            }
        }
        else -> Timber.d("initial State")
    }

}