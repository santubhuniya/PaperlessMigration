package com.paperless.app.action.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paperless.app.datamodel.NewTransactionRequest
import com.paperless.app.ui.theme.Paperless_Text_Grey
import com.paperless.app.ui.theme.paperless_font
import com.paperless.app.viewmodel.TransactionViewModel
import com.paperless.app.widget.PageHeader
import com.paperless.app.widget.PaperlessCalendar
import com.paperless.app.widget.SolidButton
import com.paperless.app.widget.TextInput
import java.util.*

@Composable
fun AddIncomeCard(navHostController: NavHostController){

    val transactionViewModel : TransactionViewModel = hiltViewModel()
    val addTransaction = transactionViewModel.addATransaction.value

    val incomeTitle = remember {
        mutableStateOf("")
    }
    val incomeAmount = remember {
        mutableStateOf("0.00")
    }
    // add expense
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.size(16.dp))
        PageHeader("Add Income")
        PaperlessCalendar()
        Spacer(modifier = Modifier.size(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            TextInput(
                label = "Income Title",
                hint = "Enter income title",
                inputText = incomeTitle
            )
            Spacer(modifier = Modifier.size(16.dp))
            TextInput(
                label = "Income Amount",
                hint = "Enter income amount",
                inputText = incomeAmount
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "Income Source",
                style = MaterialTheme.typography.paperless_font.body1,
                color =
                MaterialTheme.colors.Paperless_Text_Grey,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(8.dp))
            // list of expense category
            Spacer(modifier = Modifier.size(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SolidButton("Save Expense") {
                    transactionViewModel.addNewTranscation(
                        newTransactionRequest = NewTransactionRequest(
                            userId = 3,
                            amount = incomeAmount.value.toFloat(),
                            transactionDate = Date().time,
                            transactionTypeId = 2,
                            transactionTypeLevel = 0,
                            transactionMode = "credit",
                            transactionSource = "manual",
                            paidBy = "cash",
                            isSourceCorrect = true,
                            transactionTitle = incomeTitle.value
                        )
                    )
                }
            }

        }


    }

}