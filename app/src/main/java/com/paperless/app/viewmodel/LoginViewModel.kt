package com.paperless.app.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import com.paperless.app.datamodel.LoginRequest
import com.paperless.app.datamodel.LoginResponse
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.repo.PaperlessRepository
import com.paperless.app.repo.SharedPrefRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val paperlessRepo: PaperlessRepository,
    sharedPrefRepo: SharedPrefRepo,
    application: Application
) : BaseViewModel(application,sharedPrefRepo) {

    val loginResponse : MutableState<NetworkResponse<LoginResponse>>
        = mutableStateOf(NetworkResponse.InitialState())
    val isLoading : MutableState<Boolean> =
        mutableStateOf(false)


    fun startLogin(loginRequest: LoginRequest){
        loginResponse.value = NetworkResponse.Loading()
        viewModelScope.launch {
            paperlessRepo.doLogin(loginRequest).fold(
                {
                    Timber.d("login failed with exception - ${it.message}")
                    loginResponse.value = NetworkResponse.Error("${it.message}")
                },
                {
                    it?.loginResponse?.let {
                        loginResponse.value = NetworkResponse.Completed(it)
                        saveUserProfile(it)
                    }
                }
            )
        }

    }

}