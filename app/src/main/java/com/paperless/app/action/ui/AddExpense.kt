package com.paperless.app.action.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paperless.app.Screens
import com.paperless.app.datamodel.NewTransactionRequest
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.ui.theme.Paperless_Text_Black
import com.paperless.app.ui.theme.Paperless_Text_Grey
import com.paperless.app.ui.theme.paperless_font
import com.paperless.app.viewmodel.NavigationViewModel
import com.paperless.app.viewmodel.TransactionViewModel
import com.paperless.app.widget.*
import timber.log.Timber
import java.util.*

@Composable
fun AddExpenseCard(
    navHostController: NavHostController,
    navigationViewModel: NavigationViewModel
) {

    val transactionViewModel: TransactionViewModel = hiltViewModel()
    val addTransaction = transactionViewModel.addATransaction.value
    val recentExpense = transactionViewModel.lastFiveTranscation.value
    LaunchedEffect(Unit) {
        transactionViewModel.getLastFiveTransaction(3, "debit")
        navigationViewModel.setupHeaderAndFooter(Screens.AddExpense)
    }
    val expenseTitle = remember {
        mutableStateOf("")
    }
    val expenseAmount = remember {
        mutableStateOf("0.00")
    }

    val transactionDate = remember {
        mutableStateOf(Date().time)
    }

    val paidBy = remember {
        mutableStateOf(false)
    }
    when (addTransaction) {
        is NetworkResponse.Completed -> {
            Timber.d("added successfully")
            expenseTitle.value = ""
            expenseAmount.value = "0.00"
            transactionViewModel.getLastFiveTransaction(3, "debit")
        }
        is NetworkResponse.Loading -> Timber.d("Loading")
        is NetworkResponse.Error -> Timber.d("Error please try again")
        is NetworkResponse.InitialState -> Timber.d("initial state")
    }
    // add expense
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.size(16.dp))
        PageHeader("Add Expense")
        PaperlessCalendar() {
            transactionDate.value = it
        }
        Spacer(modifier = Modifier.size(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            TextInput(
                label = "Expense Title",
                hint = "Enter expense title",
                inputText = expenseTitle
            )
            Spacer(modifier = Modifier.size(16.dp))
            TextInput(
                label = "Expense Amount",
                hint = "Enter expense amount",
                inputText = expenseAmount
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "Expense category",
                style = MaterialTheme.typography.paperless_font.body1,
                color =
                MaterialTheme.colors.Paperless_Text_Grey,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(8.dp))
            // list of expense category

            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "Invoice",
                style = MaterialTheme.typography.paperless_font.body1,
                color =
                MaterialTheme.colors.Paperless_Text_Grey,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colors.Paperless_Text_Grey,
                        RoundedCornerShape(10.dp)
                    )
                    .height(45.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LocalImage(
                    imageId = com.paperless.app.R.drawable.add_solid,
                    contentDes = "Add invoice",
                    width = 15.dp,
                    color = MaterialTheme.colors.Paperless_Text_Grey
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Add invoice",
                    style = MaterialTheme.typography.paperless_font.body2,
                    color =
                    MaterialTheme.colors.Paperless_Text_Grey,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.size(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SolidButton("Save Expense") {
                    transactionViewModel.addNewTranscation(
                        newTransactionRequest = NewTransactionRequest(
                            userId = 3,
                            amount = expenseAmount.value.toFloat(),
                            transactionDate = transactionDate.value,
                            transactionTypeId = 2,
                            transactionTypeLevel = 0,
                            transactionMode = "debit",
                            transactionSource = "manual",
                            paidBy = if (paidBy.value) "cash" else "online",
                            isSourceCorrect = true,
                            transactionTitle = expenseTitle.value
                        )
                    )
                }
            }

        }

        Spacer(modifier = Modifier.size(24.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Text(
                "Recently added expense",
                style = MaterialTheme.typography.paperless_font.body1,
                color =
                MaterialTheme.colors.Paperless_Text_Grey,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(16.dp))
            recentExpense.forEach {
                TransactionCard(transaction = it)
                Spacer(modifier = Modifier.size(16.dp))
            }
        }

        Spacer(modifier = Modifier.size(64.dp))

    }
}