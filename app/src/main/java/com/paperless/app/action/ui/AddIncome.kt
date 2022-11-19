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
import androidx.navigation.NavHostController
import com.paperless.app.ui.theme.Paperless_Text_Grey
import com.paperless.app.ui.theme.paperless_font
import com.paperless.app.widget.PageHeader
import com.paperless.app.widget.PaperlessCalendar
import com.paperless.app.widget.SolidButton
import com.paperless.app.widget.TextInput

@Composable
fun AddIncomeCard(navHostController: NavHostController){
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
                SolidButton("Save Expense") {}
            }

        }


    }

}