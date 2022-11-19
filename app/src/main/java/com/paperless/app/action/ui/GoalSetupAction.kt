package com.paperless.app.action.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paperless.app.datamodel.GoalSummaryData
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.ui.theme.*
import com.paperless.app.viewmodel.GoalViewModel
import com.paperless.app.widget.GenericBudgetRow
import com.paperless.app.widget.LocalImage
import com.paperless.app.widget.PageHeader
import com.paperless.app.widget.getMMMYYYY
import timber.log.Timber
import java.util.*

@Composable
fun GoalSetupAction(navHostController: NavHostController){
    val goalViewModel : GoalViewModel = hiltViewModel()
    val goalList = goalViewModel.goalList.value
    LaunchedEffect(Unit){
        goalViewModel.getGoalSummary(3)
    }

    when(goalList){
        is NetworkResponse.Completed -> ShowGoalCard(goalList.data)
        is NetworkResponse.Error -> Timber.d("Error")
        is NetworkResponse.InitialState -> Timber.d("Initial State")
        is NetworkResponse.Loading -> Timber.d("Loading")
    }
}

@Composable
fun ShowGoalCard(goalSummaryData: GoalSummaryData){

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.size(16.dp))
        PageHeader(label = "Goal Summary")
        // make top card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            backgroundColor = MaterialTheme.colors.Paperless_White,
            elevation = 5.dp,
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {

                Row(modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                    Row() {
                        LocalImage(
                            imageId = com.paperless.app.R.drawable.paperless_calendar,
                            contentDes = "Calendar icon"
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            Calendar.getInstance().timeInMillis.getMMMYYYY(),
                            style = MaterialTheme.typography.paperless_font.h5,
                            color =
                            MaterialTheme.colors.Paperless_Text_Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(40.dp)
                            .background(
                                color = MaterialTheme.colors.Paperless_Light_Card_1,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        LocalImage(
                            imageId = com.paperless.app.R.drawable.more_icon,
                            contentDes = "more transaction",
                            color = MaterialTheme.colors.Paperless_Card
                        )
                    }
                }
                Spacer(Modifier.size(24.dp))
                Text(text = "Goal for this month",
                    style = MaterialTheme.typography.paperless_font.h5,
                    color =
                    MaterialTheme.colors.Paperless_Text_Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.size(16.dp))
                Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center) {
                    LinearProgressIndicator(
                        progress = .4F,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .padding(1.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = MaterialTheme.colors.Paperless_Card,
                        backgroundColor = MaterialTheme.colors.Paperless_Light_Card_1
                    )
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                        Text(text = "$200",
                            style = MaterialTheme.typography.paperless_font.body1,
                            color =
                            MaterialTheme.colors.Paperless_Text_Black,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "$800",
                            style = MaterialTheme.typography.paperless_font.body1,
                            color =
                            MaterialTheme.colors.Paperless_Text_Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

        }

        Spacer(Modifier.size(24.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ){

            Text(text = "Your Goals",
                style = MaterialTheme.typography.paperless_font.h5,
                color =
                MaterialTheme.colors.Paperless_Text_Black,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(40.dp)
                    .background(
                        color = MaterialTheme.colors.Paperless_Light_Card_1,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                LocalImage(
                    imageId = com.paperless.app.R.drawable.more_icon,
                    contentDes = "more transaction",
                    color = MaterialTheme.colors.Paperless_Card
                )
            }

        }
        Spacer(Modifier.size(16.dp))

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            goalSummaryData.listGoals?.forEach {
                GenericBudgetRow(
                    budgetName = it.goalName,
                    currentAmount = 0.0F,
                    targetAmount = it.totalAmount
                )
                Spacer(modifier = Modifier.size(24.dp))
            }
        }

    }

}