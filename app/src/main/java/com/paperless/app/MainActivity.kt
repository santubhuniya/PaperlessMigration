package com.paperless.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.paperless.app.action.ui.BudgetSummaryAction
import com.paperless.app.action.ui.DashboardUi
import com.paperless.app.action.ui.GoalSetupAction
import com.paperless.app.ui.theme.PaperlessTheme
import com.paperless.app.ui.theme.Paperless_Background
import com.paperless.app.widget.BottomItem
import com.paperless.app.widget.PaperlessFooter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            PaperlessTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {},
                    bottomBar = {
                                PaperlessFooter(
                                    navHostController = navController,
                                    bottomItem = BottomItem.Home
                                )
                    },
                    backgroundColor = MaterialTheme.colors.Paperless_Background
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())) {
                        BuildAppRoute(navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BuildAppRoute(navHostController: NavHostController){
    
    AnimatedNavHost(navController = navHostController,  startDestination = Screens.Dashboard.name){
        composable(Screens.Dashboard.name){
            DashboardUi(navHostController = navHostController)
        }
        composable(Screens.BudgetSummary.name){
            BudgetSummaryAction(navHostController = navHostController)
        }
        composable(Screens.GoalSummary.name){
            GoalSetupAction(navHostController = navHostController)
        }

    }

}


sealed class Screens(val name: String){
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