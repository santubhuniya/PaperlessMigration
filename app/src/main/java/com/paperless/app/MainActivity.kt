package com.paperless.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.paperless.app.action.ui.*
import com.paperless.app.ui.theme.PaperlessTheme
import com.paperless.app.ui.theme.Paperless_Background
import com.paperless.app.ui.theme.Paperless_Text_Black
import com.paperless.app.ui.theme.paperless_font
import com.paperless.app.widget.BottomItem
import com.paperless.app.widget.LocalImage
import com.paperless.app.widget.PaperlessFooter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            val bottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
            )
            val coroutineScope = rememberCoroutineScope()
            PaperlessTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {},
                    bottomBar = {
                        PaperlessFooter(
                            navHostController = navController,
                            bottomItem = BottomItem.Home,
                            bottomSheetState
                        )
                    },
                    backgroundColor = MaterialTheme.colors.Paperless_Background
                ) {
                    ModalBottomSheetLayout(
                        sheetState = bottomSheetState,
                        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                        sheetContent = {
                            Column(
                                modifier = Modifier.padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp
                                )
                            ) {

                                Text(
                                    text = "Do you want to ?",
                                    style = MaterialTheme.typography.paperless_font.h4,
                                    color =
                                    MaterialTheme.colors.Paperless_Text_Black,
                                    fontWeight = FontWeight.Normal
                                )
                                Spacer(modifier = Modifier.size(16.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp).clickable {
                                            coroutineScope.launch {
                                                bottomSheetState.hide()
                                            }
                                            navController.navigate(Screens.AddIncome.name)
                                        },
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    LocalImage(
                                        imageId = R.drawable.paperless_income,
                                        contentDes = "income icon",
                                        width = 35.dp
                                    )
                                    Spacer(modifier = Modifier.size(16.dp))
                                    Text(
                                        text = "Add Income",
                                        style = MaterialTheme.typography.paperless_font.body1,
                                        color =
                                        MaterialTheme.colors.Paperless_Text_Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.size(8.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .clickable {
                                            coroutineScope.launch {
                                                bottomSheetState.hide()
                                            }
                                            navController.navigate(Screens.AddExpense.name)
                                        },
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    LocalImage(
                                        imageId = R.drawable.paperless_expense,
                                        contentDes = "expense icon",
                                        width = 35.dp
                                    )
                                    Spacer(modifier = Modifier.size(16.dp))
                                    Text(
                                        text = "Add Expense",
                                        style = MaterialTheme.typography.paperless_font.body1,
                                        color =
                                        MaterialTheme.colors.Paperless_Text_Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.size(64.dp))
                            }
                        }) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            BuildAppRoute(navController)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BuildAppRoute(navHostController: NavHostController) {

    AnimatedNavHost(navController = navHostController, startDestination = Screens.Dashboard.name) {
        composable(Screens.Dashboard.name) {
            DashboardUi(navHostController = navHostController)
        }
        composable(Screens.BudgetSummary.name) {
            BudgetSummaryAction(navHostController = navHostController)
        }
        composable(Screens.GoalSummary.name) {
            GoalSetupAction(navHostController = navHostController)
        }
        composable(Screens.AddExpense.name) {
            AddExpenseCard(navHostController = navHostController)
        }

        composable(Screens.AddIncome.name) {
            AddIncomeCard(navHostController = navHostController)
        }

        composable(Screens.MonthlyExpenseDet.name) {
            MonthlyExpenseDetails(navHostController = navHostController)
        }


    }

}


sealed class Screens(val name: String) {
    object Dashboard : Screens("dashboard")
    object Statistics : Screens("statistics")
    object AddExpense : Screens("add-expense")
    object AddIncome : Screens("add-income")
    object UserProfile : Screens("user-profile")
    object BudgetSummary : Screens("budget-summary")
    object GoalSummary : Screens("goal-summary")
    object AddNewGoal : Screens("add-new-goal")
    object Login : Screens("login")
    object Registration : Screens("registration")
    object MonthlyExpenseDet : Screens("monthly-expense")

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PaperlessTheme {
        Greeting("Android")
    }
}