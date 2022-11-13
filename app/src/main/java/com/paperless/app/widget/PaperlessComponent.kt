package com.paperless.app.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paperless.app.datamodel.BudgetSummary
import com.paperless.app.datamodel.Transaction
import com.paperless.app.ui.theme.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun DashboardTile(bgColor: Color, title: String, amount: Float = 0.00F) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp),
        backgroundColor = bgColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                ),
            horizontalAlignment = Alignment.Start
        ) {
            LocalImage(
                com.paperless.app.R.drawable.wallet,
                contentDes = "Wallet Image",
                modifier = Modifier.size(30.dp),
                color = if (bgColor == MaterialTheme.colors.Paperless_Card) MaterialTheme.colors.Paperless_White
                else MaterialTheme.colors.Paperless_Text_Grey
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                color = if (bgColor == MaterialTheme.colors.Paperless_Card) MaterialTheme.colors.Paperless_White
                else MaterialTheme.colors.Paperless_Text_Grey
            )
            Spacer(modifier = Modifier.size(40.dp))
            Text(
                "$",
                style = MaterialTheme.typography.body1,
                color = if (bgColor == MaterialTheme.colors.Paperless_Card) MaterialTheme.colors.Paperless_White
                else MaterialTheme.colors.Paperless_Text_Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                "${amount}",
                style = MaterialTheme.typography.body1,
                color = if (bgColor == MaterialTheme.colors.Paperless_Card) MaterialTheme.colors.Paperless_White
                else MaterialTheme.colors.Paperless_Text_Black,
                fontWeight = FontWeight.Bold

            )
        }
    }
}

@Composable
fun LocalImage(
    imageId: Int,
    contentDes: String,
    width: Dp = 25.dp,
    padding: Dp = 0.dp,
    color: Color = MaterialTheme.colors.Paperless_Text_Black,
    modifier: Modifier = Modifier
) {
    Timber.d("Image id -$imageId")
    Image(
        painter = painterResource(
            id = imageId
        ),
        contentScale = ContentScale.Fit,
        contentDescription = contentDes,
        modifier = modifier
            .size(width),
        colorFilter = ColorFilter.tint(color)
    )
}

@Composable
fun DashboardActionButton(
    iconId: Int,
    text: String,
    color: Color,
    onClick : ()->Unit
) {
    Card(
        modifier = Modifier
            .size(height = 120.dp, width = 150.dp)
            .padding(8.dp).clickable {
                onClick.invoke()
            },
        backgroundColor = color,
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp

    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .background(
                            color = if (color == MaterialTheme.colors.Paperless_Card)
                                MaterialTheme.colors.Paperless_Light_Card_1
                            else
                                MaterialTheme.colors.Paperless_Light_Card_2,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    LocalImage(
                        imageId = iconId,
                        contentDes = text,
                        modifier = Modifier.size(18.dp),
                        color = if (color == MaterialTheme.colors.Paperless_Card)
                            MaterialTheme.colors.Paperless_Card
                        else
                            MaterialTheme.colors.Paperless_Text_Black
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = if (color == MaterialTheme.colors.Paperless_Card)
                        MaterialTheme.colors.Paperless_White
                    else
                        MaterialTheme.colors.Paperless_Text_Black
                )
            }

        }

    }
}

@Composable
fun TransactionCard(transaction : Transaction){

    Row(modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
        ){
        
        Row(){
            Box(modifier = Modifier
                .size(50.dp)
                .background(
                    shape = RoundedCornerShape(15.dp),
                    color = MaterialTheme.colors.Paperless_Light_Card_1
                ),
                contentAlignment = Alignment.Center
            ) {
                LocalImage(
                    imageId = com.paperless.app.R.drawable.amazon,
                    contentDes = "expenses",
                    color = MaterialTheme.colors.Paperless_Card
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = transaction.txnTitle,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.Paperless_Text_Black
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = transaction.txnDate.getDDMMYYYY(),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.Paperless_Text_Grey
                )
            }
        }

        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = transaction.txnAmount.toString(),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.Paperless_Text_Black
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = transaction.txnTypeName,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.Paperless_Text_Grey
            )
        }
    }
}

fun Long.getDDMMYYYY() : String{
    val simpleDateFormat = SimpleDateFormat("dd MMM yyyy")
    return simpleDateFormat.format(Date(this))
}

@Composable
fun BudgetCard(budgetSummary: BudgetSummary){
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .size(50.dp)
            .background(
                shape = RoundedCornerShape(15.dp),
                color = MaterialTheme.colors.Paperless_Light_Card_1
            ),
            contentAlignment = Alignment.Center
        ) {
            LocalImage(
                imageId = com.paperless.app.R.drawable.amazon,
                contentDes = "expenses",
                color = MaterialTheme.colors.Paperless_Card
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = budgetSummary.expenseTypeName,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.Paperless_Text_Black
            )
            Spacer(modifier = Modifier.size(8.dp))
            ProgressBarWidget(.7)
            Spacer(modifier = Modifier.size(8.dp))
            Row(modifier =  Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
                ) {

                Text(
                    text = "${budgetSummary.expenseAmount}",
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.Paperless_Text_Grey
                )
                Text(
                    text = "${budgetSummary.budgetAmount ?: "0.00"}",
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.Paperless_Text_Grey
                )
            }

        }
    }

}
@Composable
fun ProgressBarWidget(progress: Double) {
    Timber.d("progress = $progress")
    LinearProgressIndicator(
        progress = progress.toFloat(),
        modifier = Modifier
            .fillMaxWidth()
            .height(3.dp)
            .padding(1.dp)
            .clip(RoundedCornerShape(1.dp)),
        color = MaterialTheme.colors.Paperless_Card,
        backgroundColor = MaterialTheme.colors.Paperless_Light_Card_1
    )
}
