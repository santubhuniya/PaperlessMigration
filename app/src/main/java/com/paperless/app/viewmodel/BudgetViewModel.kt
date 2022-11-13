package com.paperless.app.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.paperless.app.datamodel.BudgetExpenseSummary
import com.paperless.app.datamodel.BudgetSummary
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.repo.PaperlessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    val paperlessRepo: PaperlessRepository,
    application: Application
) : BaseViewModel(application = application) {

    val budgetExpenseList: MutableState<NetworkResponse<List<BudgetSummary>>> =
        mutableStateOf(NetworkResponse.InitialState())

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
}