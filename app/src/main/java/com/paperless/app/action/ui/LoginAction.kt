package com.paperless.app.action.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paperless.app.Screens
import com.paperless.app.datamodel.DeviceInfo
import com.paperless.app.datamodel.LoginMode
import com.paperless.app.datamodel.LoginRequest
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.ui.theme.*
import com.paperless.app.viewmodel.LoginViewModel
import com.paperless.app.viewmodel.NavigationViewModel
import com.paperless.app.widget.PageHeader
import com.paperless.app.widget.SolidButton
import com.paperless.app.widget.TextInput
import timber.log.Timber
import kotlin.math.log

@Composable
fun LoginAction(navHostController: NavHostController, navigationViewModel: NavigationViewModel) {

    val userEmail = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val loginViewModel : LoginViewModel = hiltViewModel()
    LaunchedEffect(Unit){
        navigationViewModel.setupHeaderAndFooter(Screens.Login)
    }

    val loginResponse = loginViewModel.loginResponse.value

    when(loginResponse){
        is NetworkResponse.Completed -> {
            Timber.d("Completed")
            loginViewModel.isLoading.value = false
            LaunchedEffect(Unit ) {
                navHostController.navigate(Screens.Dashboard.name)
            }
        }
        is NetworkResponse.Error -> {
            Timber.d("error")
            loginViewModel.isLoading.value = false
        }
        is NetworkResponse.InitialState -> Timber.d("Initial")
        is NetworkResponse.Loading -> {
            loginViewModel.isLoading.value = true
        }
    }
    Timber.d("Login response -$loginResponse , isLoading= ${loginViewModel.isLoading.value}")

    Column(
        modifier = Modifier.fillMaxHeight(1f),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Paperless",
            style = MaterialTheme.typography.paperless_font.h4,
            color =
            MaterialTheme.colors.Paperless_Text_Black,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.size(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            backgroundColor = MaterialTheme.colors.Paperless_White,
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PageHeader("Login")
                Spacer(modifier = Modifier.size(16.dp))
                TextInput(
                    label = "User Email",
                    hint = "Enter your registered email",
                    inputText = userEmail,
                    bgColor = MaterialTheme.colors.Paperless_Login_input
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextInput(
                    label = "Password",
                    hint = "Enter password",
                    inputText = password,
                    passwordVisibility = true,
                    bgColor = MaterialTheme.colors.Paperless_Login_input
                )
                Spacer(modifier = Modifier.size(24.dp))
                SolidButton("Login",
                    isLoading = loginViewModel.isLoading
                ) {
                    loginViewModel.startLogin(
                        LoginRequest(
                            userEmail = userEmail.value,
                            password = password.value,
                            loginMode = LoginMode.PASSCODE,
                            deviceInfo = DeviceInfo(
                                deviceId = "",//get device id uuid
                                deviceName = "", //get device name
                                deviceType = "android"
                            )
                        )
                    )
                }

            }

        }

    }
}