package com.paperless.app.repo

import arrow.core.Either
import com.paperless.app.datamodel.BudgetExpenseSummary
import com.paperless.app.datamodel.DashboardResponse
import com.paperless.app.service.PaperlessService

class PaperlessRepository(val paperlessService: PaperlessService) {

    suspend fun getPaperlessDashboard(userId : Long,montYear : String) : Either<Throwable, DashboardResponse?>
    = Either.catch {
        paperlessService.getTransactionSummary(
            userId,
            montYear
        ).body()
    }

    suspend fun getExpenseBudgetSummary(
        userId : Long,
        montYear: String
    ) : Either<Throwable, BudgetExpenseSummary?>
    = Either.catch {
        paperlessService.getAllBudgetList(
            userId,
            montYear
        ).body()
    }
}

sealed class NetworkResponse<out T : Any?> {
    class Loading() : NetworkResponse<Nothing>()
    class Completed<out T : Any?>(val data: T) : NetworkResponse<T>()
    class Error(val errMsg: String) : NetworkResponse<Nothing>()
    class InitialState() : NetworkResponse<Nothing>()
}