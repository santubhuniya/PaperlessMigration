package com.paperless.app.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.paperless.app.datamodel.GoalSummary
import com.paperless.app.datamodel.GoalSummaryData
import com.paperless.app.repo.NetworkResponse
import com.paperless.app.repo.PaperlessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    val paperlessRepo: PaperlessRepository,
    application: Application
) : BaseViewModel(application) {

    val goalList: MutableState<NetworkResponse<GoalSummaryData>> =
        mutableStateOf(NetworkResponse.Loading())

    fun getGoalSummary(userId: Long) {
        goalList.value = NetworkResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            paperlessRepo.getGoalSummary(userId)
                .fold({
                    Timber.d("Exception while getting goals - ${it.message}")
                    goalList.value = NetworkResponse.Error("${it.message}")
                }, {
                    it?.goalSummary?.let {
                        goalList.value = NetworkResponse.Completed(it)
                    }
                })
        }
    }
}