package com.paperless.app.widget

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paperless.app.datamodel.BudgetSummary
import com.paperless.app.datamodel.Transaction
import com.paperless.app.ui.theme.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*


@Composable
fun DashboardTile(bgColor: Color, title: String, amount: Float = 0.00F, clickFunction: () -> Unit) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .width(150.dp)
            .height(220.dp)
            .padding(8.dp)
            .clickable {
                clickFunction.invoke()
            },
        backgroundColor = bgColor
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
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
                    style = MaterialTheme.typography.paperless_font.h5,
                    color = if (bgColor == MaterialTheme.colors.Paperless_Card) MaterialTheme.colors.Paperless_White
                    else MaterialTheme.colors.Paperless_Text_Grey,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    ),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "$",
                    style = MaterialTheme.typography.paperless_font.h5,
                    color = if (bgColor == MaterialTheme.colors.Paperless_Card) MaterialTheme.colors.Paperless_White
                    else MaterialTheme.colors.Paperless_Text_Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "${amount}",
                    style = MaterialTheme.typography.paperless_font.h5,
                    color = if (bgColor == MaterialTheme.colors.Paperless_Card) MaterialTheme.colors.Paperless_White
                    else MaterialTheme.colors.Paperless_Text_Black,
                    fontWeight = FontWeight.Bold

                )
            }
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
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(height = 120.dp, width = 150.dp)
            .padding(8.dp)
            .clickable {
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
                                MaterialTheme.colors.Paperless_Light_Card_3
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
                            MaterialTheme.colors.Paperless_White
                        else
                            MaterialTheme.colors.Paperless_Text_Black
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.paperless_font.body1,
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
fun TransactionCard(transaction: Transaction) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row() {
            Box(
                modifier = Modifier
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
            Column(
                modifier = Modifier.height(50.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = transaction.txnTitle,
                    style = MaterialTheme.typography.paperless_font.body1,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.Paperless_Text_Black
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = transaction.txnDate.getDDMMYYYY(),
                    style = MaterialTheme.typography.paperless_font.body2,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.Paperless_Text_Grey
                )
            }
        }

        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = transaction.txnAmount.toString(),
                style = MaterialTheme.typography.paperless_font.body1,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.Paperless_Text_Black
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = transaction.txnTypeName,
                style = MaterialTheme.typography.paperless_font.body2,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.Paperless_Text_Grey
            )
        }
    }
}

fun Long.getDDMMYYYY(): String {
    val simpleDateFormat = SimpleDateFormat("dd MMM yyyy")
    return simpleDateFormat.format(Date(this))
}

fun Long.getMMMYYYY(): String {
    val simpleDateFormat = SimpleDateFormat(" MMM yyyy")
    return simpleDateFormat.format(Date(this))
}

@Composable
fun GenericBudgetRow(budgetName: String, currentAmount: Float?, targetAmount: Float?) {
    val showKeyboard = remember{
        mutableStateOf(false)
    }
    val budgetAmount = remember{
        mutableStateOf(targetAmount?.let {
            "$it"
        } ?: "0.0")
    }
    Column(modifier = Modifier.fillMaxWidth()) {


        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = budgetName,
                    style = MaterialTheme.typography.paperless_font.body2,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.Paperless_Text_Black
                )
                Spacer(modifier = Modifier.size(8.dp))
                ProgressBarWidget(.7)
                Spacer(modifier = Modifier.size(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "${currentAmount ?: "0.00"}",
                        style = MaterialTheme.typography.paperless_font.body2,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.Paperless_Text_Grey
                    )
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {

                        }
                    ) {

                        Text(
                            text = "${targetAmount ?: "0.00"}",
                            style = MaterialTheme.typography.paperless_font.body2,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.Paperless_Text_Black,
                            modifier = Modifier.clickable {
                                showKeyboard.value = !showKeyboard.value
                            }
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.background(
                                color = Color(0x80CCCCCC),
                                shape = RoundedCornerShape(50)
                            )
                        ) {
                            LocalImage(
                                imageId = com.paperless.app.R.drawable.edit_icon,
                                contentDes = "Edit Budget",
                                color = MaterialTheme.colors.Paperless_Text_Black,
                                width = 15.dp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)

                            )
                        }
                    }
                }

            }
        }

        if(showKeyboard.value){
            KeyboardInputComponent(data = budgetAmount, label = "Enter or update budget amount" )   {
                budgetAmount.value = it
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


//calendar component
enum class CalendarType {
    Monthly,
    Weekly
}

@Composable
fun PaperlessCalendar(
    calendarType: CalendarType = CalendarType.Weekly,
    onSelect:(Long)->Unit
) {

    val selectedDate = remember {
        mutableStateOf(Calendar.getInstance().timeInMillis)
    }

    val monthYear = remember {
        mutableStateOf(Calendar.getInstance().timeInMillis.getMMMYYYY())
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 5.dp

    ) {

        val calendarMap = mutableMapOf<Int, Long>()

        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.SUNDAY
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val startDay = calendar.get(Calendar.DATE)
        Timber.d("startDate - $startDay")
        calendar.set(Calendar.DATE, startDay)
        calendarMap.put(1, calendar.timeInMillis)
        monthYear.value = calendar.timeInMillis.getMMMYYYY()
        for (i in 2..7) {
            calendar.set(
                Calendar.DATE, calendar.get(Calendar.DATE) + 1
            )
            calendarMap.put(i, calendar.timeInMillis)
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(24.dp))
            Row() {
                LocalImage(
                    imageId = com.paperless.app.R.drawable.paperless_calendar,
                    contentDes = "Calendar icon",
                    color = MaterialTheme.colors.Paperless_Button
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    monthYear.value,
                    style = MaterialTheme.typography.paperless_font.h5,
                    color = MaterialTheme.colors.Paperless_Button,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                calendarMap.forEach {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.size(16.dp))
                        Text(
                            text = when (it.key) {
                                1 -> "S"
                                2 -> "M"
                                3 -> "T"
                                4 -> "W"
                                5 -> "T"
                                6 -> "F"
                                7 -> "S"
                                else -> "S"
                            },

                            style = MaterialTheme.typography.paperless_font.body1,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.Paperless_Text_Grey
                        )
                        Spacer(modifier = Modifier.size(24.dp))
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = if (isSelectedDate(it.value, selectedDate.value))
                                        MaterialTheme.colors.Paperless_Button
                                    else Color.Transparent,
                                    shape = CircleShape
                                )
                        ) {

                            Text(
                                text = "${it.value.getDateFromTime()}",
                                style = MaterialTheme.typography.paperless_font.body1,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelectedDate(it.value, selectedDate.value))
                                    MaterialTheme.colors.Paperless_White
                                else
                                    MaterialTheme.colors.Paperless_Text_Black,
                                modifier = Modifier.clickable {
                                    selectedDate.value = it.value
                                    onSelect.invoke(it.value)
                                }
                            )
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

    }
}

fun isSelectedDate(date1: Long, date2: Long): Boolean {
    return date1.getDDMMYYYY() == date2.getDDMMYYYY()
}


fun Long.getDateFromTime(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.DATE)
}

@Composable
fun PaperlessAmountDisplay(amount: Float) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(
                color = MaterialTheme.colors.Paperless_Light_Card_1,
                shape = CircleShape
            ), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .background(
                    color = MaterialTheme.colors.Paperless_Card,
                    shape = CircleShape
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$ $amount",
                style = MaterialTheme.typography.paperless_font.h3,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.Paperless_White
            )
        }
    }
}

@Composable
fun TextInput(
    label: String,
    hint: String,
    inputText: MutableState<String>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.paperless_font.body1,
            color =
            MaterialTheme.colors.Paperless_Text_Grey,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(8.dp))

        OutlinedTextField(
            value = inputText.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.paperless_font.body2
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = MaterialTheme.colors.Paperless_Text_Black,
                backgroundColor = MaterialTheme.colors.Paperless_input_background,
                focusedBorderColor = Color(0xAA3C3DBF),
                unfocusedBorderColor = MaterialTheme.colors.Paperless_input_background
            ),
            onValueChange = { value ->
                inputText.value = value
            },
            shape = RoundedCornerShape(10.dp),
            textStyle = MaterialTheme.typography.paperless_font.body1,
            singleLine = true
        )
    }
}

@Composable
fun SearchInput(
    label: String,
    inputText: MutableState<String>,
    suffixClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(
                color = MaterialTheme.colors.Paperless_Light_Card_3,
                shape = RoundedCornerShape(5.dp)
            ),
        verticalArrangement = Arrangement.Center

    ) {
        BasicTextField(
            value = inputText.value,
            onValueChange = { value ->
                inputText.value = value
            },
            maxLines = 1,
            textStyle = MaterialTheme.typography.paperless_font.body1,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        )
                        .offset(x = 8.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            if (inputText.value.isEmpty()) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.paperless_font.body2,
                                    color = MaterialTheme.colors.Paperless_Text_Black

                                )
                            }
                        }

                        if (inputText.value.length > 0) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Clear",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        inputText.value = ""
                                    },
                                tint = MaterialTheme.colors.Paperless_Text_Grey
                            )
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.size(0.dp))
                        innerTextField()
                    }
                }
            }
        )
    }
}

@Composable
fun SolidButton(
    name: String,
    modifier: Modifier = Modifier,
    isTransparent : Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(45.dp)
            .width(170.dp)
            ,
        onClick = {
            onClick.invoke()
        },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if(isTransparent)  Color.Transparent else MaterialTheme.colors.Paperless_Button,
            contentColor = MaterialTheme.colors.Paperless_White
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 1.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.paperless_font.body1,
            color = if(isTransparent) MaterialTheme.colors.Paperless_Text_Black else MaterialTheme.colors.Paperless_White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PageHeader(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.paperless_font.h4,
        color =
        MaterialTheme.colors.Paperless_Text_Black,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.size(24.dp))
}

@Composable
fun TabBarHeader(headerList : List<String>,
                 selected : String,
                 onClick: (String) -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 16.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceAround

    ){
        headerList.forEach { label->
            Column(modifier = Modifier.clickable {
                onClick.invoke(label)
            }, horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = label.uppercase(Locale.getDefault()),
                    style = MaterialTheme.typography.paperless_font.body1,
                    color =
                    if (selected == label)
                        MaterialTheme.colors.Paperless_Text_Black
                    else MaterialTheme.colors.Paperless_Text_Grey,
                    fontWeight = if (selected == label)
                        FontWeight.SemiBold
                    else
                        FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(8.dp))
                if(selected == label) {
                    Divider(
                        thickness = 2.dp,
                        color = MaterialTheme.colors.Paperless_Button,
                        modifier = Modifier.width(50.dp)
                    )
                }
            }
            Spacer(modifier =  Modifier.size(16.dp))
        }

    }
}

@Composable
fun KeyboardInputComponent(data : MutableState<String> ,label: String,callback : (String)->Unit) {
    val showKeyBoard = remember {
        mutableStateOf(true)
    }
    val showDone = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top

        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = data.value,
                    style = MaterialTheme.typography.paperless_font.h1,
                    color = MaterialTheme.colors.Paperless_Text_Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        showKeyBoard.value = !showKeyBoard.value
                    }
                )
                Divider(
                    thickness = 2.dp,
                    color = MaterialTheme.colors.Paperless_Text_Black,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(vertical = 3.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.paperless_font.body2,
                    color = MaterialTheme.colors.Paperless_Text_Grey,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
            if(showDone.value) {
                LocalImage(
                    imageId = com.paperless.app.R.drawable.done_icon,
                    contentDes = "correct amount",
                    color = MaterialTheme.colors.Paperless_Button,
                    modifier = Modifier.size(75.dp).padding(top = 10.dp).clickable {
                        showDone.value = false
                        showKeyBoard.value = false
                    }
                )
            }else{
                Spacer(modifier = Modifier.size(75.dp))
            }
        }

        val keyboardList = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "<-", "0", ".")
        if (showKeyBoard.value) {
            LazyVerticalGrid(
                modifier = Modifier
                    .height(250.dp)
                    .padding(horizontal = 0.dp),
                columns = GridCells.Fixed(3), content =
                {
                    items(keyboardList.size) {
                        SolidButton(
                            keyboardList[it],
                            isTransparent = true,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            val char = keyboardList[it]
                            when (char) {
                                "C" -> data.value = "0.00"
                                "<-" -> if (data.value.length > 0) data.value =
                                    data.value.substring(0, data.value.length - 1) else data.value =
                                    "0.00"
                                "done" -> callback(data.value)
                                "." -> if (!data.value.contains(".")) data.value = "${data.value}."
                                else ->
                                    if (data.value == "0.00") {
                                        data.value = char
                                    } else if (data.value.contains(".") && data.value.substring(
                                            data.value.indexOf(
                                                "."
                                            ) + 1, data.value.length
                                        ).length < 2
                                    ) {
                                        data.value = "${data.value}$char"
                                    } else if (!data.value.contains(".")) {
                                        data.value = "${data.value}$char"
                                    }
                            }
                            callback(data.value)
                            if(!showDone.value){
                                showDone.value = true
                            }
                        }
                    }
                }
            )
        }

    }
}


