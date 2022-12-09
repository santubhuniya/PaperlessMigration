package com.paperless.app.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
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
class BudgetViewModel @Inject constructor(
    val paperlessRepo: PaperlessRepository,
    application: Application,
    sharedPrefRepo : SharedPrefRepo
) : BaseViewModel(application = application, sharedPrefRepo = sharedPrefRepo) {

    val budgetExpenseList: MutableState<NetworkResponse<List<BudgetSummary>>> =
        mutableStateOf(NetworkResponse.InitialState())

    val newBudgetResp : MutableState<NetworkResponse<NewBudgetResp>> = mutableStateOf(NetworkResponse.InitialState())

    fun getBudgetDetails(userId: Long, monthYear: String) {
        budgetExpenseList.value = NetworkResponse.Loading()
        viewModelScope.launch (Dispatchers.IO){

            paperlessRepo.getExpenseBudgetSummary(
                userId,
                monthYear
            ).fold(
                {
                    Timber.d("Exception whilefetching data - ${it.message}")
                    budgetExpenseList.value = NetworkResponse.Error("${it.message}")
                },
                {
                    it?.budgetSummary?.let {
                        budgetExpenseList.value = NetworkResponse.Completed(it)
                    }
                }
            )

        }
    }

    fun addUpdateBudget(newBudgetRequest: NewBudgetRequest){
        viewModelScope.launch(Dispatchers.IO) {
            paperlessRepo.addNewUpdateBudget(
                newBudgetRequest
            ).fold(
                {
                    Timber.d("exception for adding budget - ${it.message}")
                    newBudgetResp.value = NetworkResponse.Error("${it.message}")
                },
                {
                    it?.budgetResp?.let{
                        newBudgetResp.value = NetworkResponse.Completed(it)
                    }
                }
            )
        }
    }
}