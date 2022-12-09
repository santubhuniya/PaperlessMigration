package com.paperless.app.repo

import arrow.core.Either
import com.paperless.app.datamodel.*
import com.paperless.app.service.LoginService
import com.paperless.app.service.PaperlessService

class PaperlessRepository(
    val paperlessService: PaperlessService,
    val loginService: LoginService
) {

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

    suspend fun getGoalSummary(
        userId: Long
    ) : Either<Throwable,GoalSumaryResponse?>
    = Either.Companion.catch {
        paperlessService.getAllGoals(userId)
            .body()
    }

    suspend fun getMonthlyExpenseDetails(userId: Long, montYear: String)
    : Either<Throwable, MonthlyExpenseResponse?>
    = Either.catch {
        paperlessService.getMonthlyTransactionDetails(userId,montYear).body()
    }

    suspend fun getChartSummary(
        chartRequest: ChartRequest
    ) : Either<Throwable,ChartResponse?>
    = Either.catch {
        paperlessService.getChartSummary(chartRequest = chartRequest).body()
    }

    suspend fun addNewTransaction(
        newTransactionRequest: NewTransactionRequest
    ) : Either<Throwable,NewTransactionResponse?>
    = Either.catch {
        paperlessService.addNewTransaction(newTransactionRequest = newTransactionRequest).body()
    }

    suspend fun getRecentTransaction(
        userId: Long,
        txnType : String
    ) : Either<Throwable,RecentTransactionResp?>
    = Either.catch {
        paperlessService.getLastFiveTransaction(
            userId = userId,
            transactionType = txnType
        ).body()
    }

    suspend fun addNewUpdateBudget(
        newBudgetRequest: NewBudgetRequest
    ) : Either<Throwable,BudgetResponse?>
    = Either.catch {
        paperlessService.addUpdateBudget(newBudgetRequest)
            .body()
    }

    suspend fun doLogin(
        loginRequest: LoginRequest
    ) : Either<Throwable,LoginReponseData?>
    = Either.catch {
        loginService.doLogin(loginRequest)
            .body()
    }


}

sealed class NetworkResponse<out T : Any?> {
    class Loading() : NetworkResponse<Nothing>()
    class Completed<out T : Any?>(val data: T) : NetworkResponse<T>()
    class Error(val errMsg: String) : NetworkResponse<Nothing>()
    class InitialState() : NetworkResponse<Nothing>()
}