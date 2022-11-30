package com.paperless.app.viewmodel

import android.app.Application
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.paperless.app.R
import com.paperless.app.Screens
import com.paperless.app.repo.PaperlessRepository
import com.paperless.app.ui.theme.Paperless_Button
import com.paperless.app.ui.theme.Paperless_Text_Black
import com.paperless.app.ui.theme.paperless_font
import com.paperless.app.widget.Actions
import com.paperless.app.widget.BottomItem
import com.paperless.app.widget.LocalImage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class NavigationViewModel @Inject constructor(
    val paperlessRepo: PaperlessRepository,
    application: Application
) : BaseViewModel(application) {

    val actions : MutableState<List<Actions>> = mutableStateOf(listOf())
    val selectedFooter : MutableState<BottomItem> = mutableStateOf(BottomItem.Home)
    val showBack :MutableState<Boolean> = mutableStateOf(false)

    val headerActions =
        Actions(
            widget = {
                Row (verticalAlignment = Alignment.CenterVertically){
                    LocalImage(imageId = R.drawable.sign_out, contentDes = "Sign out")
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "Sign Out",
                        style = MaterialTheme.typography.paperless_font.body2,
                        color =
                        MaterialTheme.colors.Paperless_Text_Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            contentDescription = "Sign Off"
        ){

        }


    fun setupHeaderAndFooter(screens: Screens){
        showBack.value = true
        actions.value = listOf(headerActions)
        when(screens){
            is Screens.Dashboard ->{
                actions.value = listOf(
                    Actions(
                        widget = {
                            LocalImage(
                                imageId = R.drawable.bell_notification,
                                contentDes = "paperless notification",
                                width = 30.dp
                            )
                        },
                        contentDescription = "paperless notification",
                        badgeCount = 10
                    ){

                    },
                    Actions(
                    widget = {
                        LocalImage(
                            imageId = R.drawable.search_transaction,
                            contentDes = "Search expense",
                            width = 30.dp
                        )
                    },
                    contentDescription = "Search expense"
                ){

                },headerActions)

                showBack.value = false

                selectedFooter.value = BottomItem.Home
            }
            is Screens.Statistics ->{
                selectedFooter.value = BottomItem.Statistics
            }
            is Screens.AddIncome,Screens.AddExpense,Screens.AddNewGoal ->{
                selectedFooter.value = BottomItem.AddButton
            }
            else ->{
                actions.value = listOf(headerActions)
            }
        }
    }

}