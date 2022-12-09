package com.paperless.app.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import com.paperless.app.action.ui.ChartType
import com.paperless.app.action.ui.getCurrentMonthYear
import com.paperless.app.datamodel.*
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.repo.PaperlessRepository
import com.paperless.app.repo.SharedPrefRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject
constructor(
    val paperlessRepo: PaperlessRepository,
    application: Application,
    sharedPrefRepo: SharedPrefRepo
) : BaseViewModel(application,sharedPrefRepo) {

    val addATransaction : MutableState<NetworkResponse<Long>> = mutableStateOf(NetworkResponse.InitialState())
    val monthlyTranscationDetails : MutableState<NetworkResponse<MonthlyExpenseDetails>> = mutableStateOf(NetworkResponse.InitialState())
    val statisticsResponse : MutableState<NetworkResponse<ChartSummary>> = mutableStateOf(NetworkResponse.InitialState())
    val selectedChartType : MutableState<String> = mutableStateOf(ChartType.weekly.name)
    val lastFiveTranscation : MutableState<List<Transaction>> = mutableStateOf(listOf())


    fun addNewTranscation(newTransactionRequest: NewTransactionRequest){
        // recent transcation will be transction for today
        addATransaction.value = NetworkResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {

            paperlessRepo.addNewTransaction(newTransactionRequest).fold(
                {
                    Timber.d("Exception - ${it.message}")
                    addATransaction.value =  NetworkResponse.Error("${it.message}")
                },
                {
                    it?.transactionId?.let {
                        addATransaction.value = NetworkResponse.Completed(it)
                    }
                }
            )
        }
    }

    fun getMonthlyExpenseDetails(){
        monthlyTranscationDetails.value = NetworkResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            paperlessRepo.getMonthlyExpenseDetails(
                userId = 3,
                montYear = getCurrentMonthYear()
            ).fold({
                Timber.d("Error fetching details - ${it.message}")
                monthlyTranscationDetails.value = NetworkResponse.Error("${it.message}")
            },{
                it?.expenseDetails?.let {
                    monthlyTranscationDetails.value = NetworkResponse.Completed(it)
                }
            }
            )
        }
    }

    fun getStatisticsData(chartRequest: ChartRequest){
        statisticsResponse.value = NetworkResponse.Loading()
        viewModelScope.launch (Dispatchers.IO){
            paperlessRepo.getChartSummary(chartRequest).fold(
                {
                    Timber.d("Exception -${it.message}")
                    statisticsResponse.value = NetworkResponse.Error("${it.message}")
                },
                {
                    it?.chartSummary?.let{
                        statisticsResponse.value = NetworkResponse.Completed(it)
                    }
                }
            )
        }

    }

    fun getLastFiveTransaction(
        userId : Long,
        txnType : String
    ){
        viewModelScope.launch (Dispatchers.IO){
            paperlessRepo.getRecentTransaction(
                userId = userId,
                txnType = txnType
            ).fold(
                {
                    Timber.d("error -${it.message}")
                },
                {
                    it?.transactionList?.let {
                        lastFiveTranscation.value = it
                    }
                }
            )
        }
    }

}