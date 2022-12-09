package com.paperless.app.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.paperless.app.R
import com.paperless.app.Screens
import com.paperless.app.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PaperlessFooter(
    navHostController: NavHostController,
    bottomItem: BottomItem,
    modalBottomSheetState: ModalBottomSheetState

) {

    val bottomItems = listOf<BottomItem>(
        BottomItem.Home,
        BottomItem.Statistics,
        BottomItem.AddButton,
        BottomItem.Savings,
        BottomItem.Settings
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.Paperless_White,
        contentColor = MaterialTheme.colors.Paperless_Text_Black,
        elevation = 5.dp
    ) {
        val coroutineScope = rememberCoroutineScope()
        bottomItems.forEach {
            BottomNavigationItem(
                selected = bottomItem == it,
                onClick = {
                    when (it.label) {
                        BottomItem.Home.label -> navHostController.navigate(Screens.Dashboard.name) {
                            popUpTo(Screens.Dashboard.name) {
                                inclusive = true
                                saveState = false
                            }
                        }
                        BottomItem.AddButton.label -> coroutineScope.launch {
                            if (!modalBottomSheetState.isVisible) {
                                modalBottomSheetState.show()
                            }
                        }

                        BottomItem.Statistics.label -> navHostController.navigate(
                            Screens.Statistics.name,
                        ) {
                            popUpTo(Screens.Statistics.name) {
                                inclusive = true
                                saveState = false
                            }
                        }
//                        BottomItem.Menu.label -> navHostController.navigate(
//                            Screens.MoreMenu().name){
//                            popUpTo(Screens.MoreMenu().name){
//                                inclusive = true
//                                saveState = false
//                            }
//                        }
                    }
                },
                selectedContentColor = MaterialTheme.colors.Paperless_White,
                unselectedContentColor = MaterialTheme.colors.Paperless_Text_Black,
                alwaysShowLabel = false,
                icon = {
                    if (it == BottomItem.AddButton) {
                        LocalImage(
                            imageId = it.icon, contentDes = it.label,
                            color = MaterialTheme.colors.Paperless_Button,
                            width = 50.dp
                        )
                    } else {
                        LocalImage(
                            imageId = if (bottomItem != it) it.icon else it.selectedIcon,
                            contentDes = it.label,
                            color = if (bottomItem != it) MaterialTheme.colors.Paperless_Text_Grey else MaterialTheme.colors.Paperless_Text_Black
                        )
                    }
                }
            )
        }
    }

}


sealed class BottomItem(val icon: Int, val selectedIcon: Int, val label: String) {
    object Home : BottomItem(
        icon = com.paperless.app.R.drawable.outline_home,
        selectedIcon = com.paperless.app.R.drawable.paperless_home_solid,
        label = "Home"
    )

    object Statistics : BottomItem(
        icon = com.paperless.app.R.drawable.outline_stat,
        selectedIcon = com.paperless.app.R.drawable.solid_stat,
        label = "Statistics"
    )

    object AddButton : BottomItem(
        icon = com.paperless.app.R.drawable.add_solid,
        selectedIcon = com.paperless.app.R.drawable.add_solid,
        label = "AddNew"
    )

    object Savings : BottomItem(
        icon = com.paperless.app.R.drawable.paperless_wallet_outline,
        selectedIcon = com.paperless.app.R.drawable.paperless_wallet_solid,
        label = "Savings"
    )

    object Settings : BottomItem(
        icon = com.paperless.app.R.drawable.outline_settings,
        selectedIcon = com.paperless.app.R.drawable.solid_settings, "Menu"
    )
}

@Composable
fun HeaderCard(
    navHostController: NavHostController,
    actionButton: List<Actions> = listOf(),
    showBack : Boolean = true
) {

    TopAppBar(
        backgroundColor = MaterialTheme.colors.Paperless_Background,
        elevation = 0.dp,
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // show back arrow
            if(showBack) {
                LocalImage(
                    imageId = R.drawable.back_paperless, contentDes = "back arrow",
                     color = MaterialTheme.colors.Paperless_Text_Black,
                    modifier = Modifier.size(20.dp).clickable {
                        navHostController.popBackStack()
                    }
                )
            }else{
                //show branding icon
                Spacer(modifier = Modifier.size(40.dp))
            }

            Row() {
                actionButton.forEach {
                    Spacer(modifier = Modifier.size(12.dp))
                    ActionButton(it, navHostController)
                }
            }
        }
    }

}

@Composable
fun ActionButton(
    action: Actions,
    navHostController: NavHostController
) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .clickable {
                action.action.invoke(navHostController)
            },
        contentAlignment = Alignment.TopEnd

    ) {

        Box(
            modifier = Modifier
                .height(40.dp),
            contentAlignment = Alignment.Center
        ) {
            action.widget()
        }
        // item badge
        if (action.badgeCount > 0)
            Box(
                modifier = Modifier
                    .height(18.dp)
                    .padding(end = 8.dp, top = 0.dp)
                    .background(
                        color = MaterialTheme.colors.Paperless_Red,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .defaultMinSize(18.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "${action.badgeCount}",
                    style = MaterialTheme.typography.paperless_font.caption,
                    color = MaterialTheme.colors.Paperless_White,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }

    }
}

data class Actions(
    val widget: @Composable() () -> Unit,
    val contentDescription: String = "",
    val badgeCount: Int = 0,
    val color: Color? = null,
    val action: (NavHostController) -> Unit
)