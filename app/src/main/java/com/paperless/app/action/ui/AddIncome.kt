package com.paperless.app.action.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paperless.app.Screens
import com.paperless.app.datamodel.NewTransactionRequest
import com.paperless.app.datamodel.NewTransactionResponse
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.ui.theme.Paperless_Text_Grey
import com.paperless.app.ui.theme.paperless_font
import com.paperless.app.viewmodel.NavigationViewModel
import com.paperless.app.viewmodel.TransactionViewModel
import com.paperless.app.widget.*
import timber.log.Timber
import java.util.*

@Composable
fun AddIncomeCard(
    navHostController: NavHostController,
    navigationViewModel: NavigationViewModel
){

    val transactionViewModel : TransactionViewModel = hiltViewModel()
    val addTransaction = transactionViewModel.addATransaction.value
    val recentIncome = transactionViewModel.lastFiveTranscation.value

    val incomeTitle = remember {
        mutableStateOf("")
    }
    val incomeAmount = remember {
        mutableStateOf("0.00")
    }

    LaunchedEffect(Unit){
        transactionViewModel.getLastFiveTransaction(3,"credit")
        navigationViewModel.setupHeaderAndFooter(Screens.AddIncome)
    }
    val transactionDate = remember{
        mutableStateOf(Date().time)
    }


    when(addTransaction){
        is NetworkResponse.Completed -> {
            Timber.d("added successfully")
            incomeTitle.value = ""
            incomeAmount.value = "0.00"
            transactionViewModel.getLastFiveTransaction(3,"credit")
        }
        is NetworkResponse.Loading -> Timber.d("Loading")
        is NetworkResponse.Error ->Timber.d("Error please try again")
        is NetworkResponse.InitialState -> Timber.d("initial state")
    }
    // add expense
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.size(16.dp))
        PageHeader("Add Income")
        PaperlessCalendar(){
            transactionDate.value = it
        }
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
            KeyboardInputComponent(incomeAmount,"Enter income amount"){ t1->
                incomeAmount.value = t1
            }
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
                SolidButton("Save Income") {
                    transactionViewModel.addNewTranscation(
                        newTransactionRequest = NewTransactionRequest(
                            userId = 3,
                            amount = incomeAmount.value.toFloat(),
                            transactionDate = transactionDate.value,
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

            Spacer(modifier = Modifier.size(24.dp))
            Text("Recent Income added",
                style = MaterialTheme.typography.paperless_font.body1,
                color =
                MaterialTheme.colors.Paperless_Text_Grey,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(16.dp))
            recentIncome.forEach {
                TransactionCard(transaction = it)
                Spacer(modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.size(64.dp))

        }


    }

}