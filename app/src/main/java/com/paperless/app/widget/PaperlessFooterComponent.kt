package com.paperless.app.widget

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.paperless.app.Screens
import com.paperless.app.ui.theme.*

@Composable
fun PaperlessFooter(navHostController: NavHostController,bottomItem: BottomItem){

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
        bottomItems.forEach {
            BottomNavigationItem(
                selected = bottomItem == it,
                onClick = {
                    when (it.label) {
                        BottomItem.Home.label -> navHostController.navigate(Screens.Dashboard.name){
                            popUpTo(Screens.Dashboard.name){
                                inclusive = true
                                saveState = false
                            }
                        }
//                        BottomItem.Home.label -> navHostController.navigate(Screens.Dashboard().name){
//                            popUpTo(Screens.Dashboard().name){
//                                inclusive = true
//                                saveState = false
//                            }
//                        }
//                        BottomItem.ShoppingList.label -> navHostController.navigate(
//                            Screens.ShoppingList().name,
//                        ){
//                            popUpTo(Screens.ShoppingList().name){
//                                inclusive = true
//                                saveState = false
//                            }
//                        }
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
                    if(it==BottomItem.AddButton){
                        LocalImage(imageId = it.icon, contentDes = it.label,
                            color =  MaterialTheme.colors.Paperless_Button,
                            width = 50.dp
                        )
                    }else {
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


sealed class BottomItem(val icon: Int,val selectedIcon :Int, val label: String) {
    object Home : BottomItem(
        icon = com.paperless.app.R.drawable.outline_home,
        selectedIcon = com.paperless.app.R.drawable.paperless_home_solid,
        label = "Home"
    )

    object Statistics : BottomItem(
        icon = com.paperless.app.R.drawable.outline_stat,
        selectedIcon = com.paperless.app.R.drawable.solid_stat,
        label = "Shopping")

    object AddButton : BottomItem(
        icon = com.paperless.app.R.drawable.add_solid,
        selectedIcon = com.paperless.app.R.drawable.add_solid,
        label = "Shopping")

    object Savings : BottomItem(
        icon = com.paperless.app.R.drawable.paperless_wallet_outline,
        selectedIcon = com.paperless.app.R.drawable.paperless_wallet_solid,
        label = "Savings")

    object Settings : BottomItem(icon = com.paperless.app.R.drawable.outline_settings,
        selectedIcon = com.paperless.app.R.drawable.solid_settings
        , "Menu")
}