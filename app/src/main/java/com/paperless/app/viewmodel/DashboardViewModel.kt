package com.paperless.app.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.paperless.app.datamodel.DashboardResponse
import com.paperless.app.datamodel.TransactionSummary
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.repo.PaperlessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(val paperlessRepo: PaperlessRepository, application: Application) : BaseViewModel(application = application) {

    val dashboardData : MutableState<NetworkResponse<TransactionSummary>> = mutableStateOf(NetworkResponse.InitialState())

    fun getPaperlessDashboard(userId : Long, monthYear: String){
        Timber.d("Calling paperless dashboard user id -$userId monthYear - $monthYear")
        dashboardData.value = NetworkResponse.Loading()
        viewModelScope.launch {
            paperlessRepo.getPaperlessDashboard(
                userId = userId,
                montYear = monthYear
            ).fold(
                {
                    Timber.d("Paperless dashboard response error - ${it.message}")
                    dashboardData.value = NetworkResponse.Error("${it.message}")
                },
                { response->
                    response?.transactionSummary?.let {
                        dashboardData.value = NetworkResponse.Completed(it)
                    }

                }
            )
        }
    }
}