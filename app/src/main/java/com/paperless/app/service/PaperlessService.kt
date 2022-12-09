package com.paperless.app.service

import com.paperless.app.datamodel.*
import retrofit2.Response
import retrofit2.http.*


interface PaperlessService {
    @GET("transaction/summary/{userId}/{monthYear}")
    suspend fun getTransactionSummary(
        @Path("userId") userId : Long,
        @Path("monthYear") monthYear : String
    ) : Response<DashboardResponse?>

    @GET("budget/budget/all/{userId}/{monthYear}")
    suspend fun getAllBudgetList(
        @Path("userId") userId : Long,
        @Path("monthYear") monthYear : String
    ) : Response<BudgetExpenseSummary?>

    @GET("budget/goals/{userId}")
    suspend fun getAllGoals(
        @Path("userId") userId : Long
    ) : Response<GoalSumaryResponse?>

    @GET("transaction/details/{userId}/{monthYear}")
    suspend fun getMonthlyTransactionDetails(
        @Path("userId") userId : Long,
        @Path("monthYear") monthYear : String
    ) : Response<MonthlyExpenseResponse?>

    @POST("transaction/statistics")
    suspend fun getChartSummary(
        @Body chartRequest: ChartRequest
    ) : Response<ChartResponse?>

    @POST("transaction/add")
    suspend fun addNewTransaction(
        @Body newTransactionRequest: NewTransactionRequest
    ) : Response<NewTransactionResponse?>

    @GET("transaction/lastfive/{userId}/{txnType}")
    suspend fun getLastFiveTransaction(
        @Path("userId") userId : Long,
        @Path("txnType") transactionType : String
    ) : Response<RecentTransactionResp?>


    @POST("budget/new")
    suspend fun addUpdateBudget(
        @Body newBudgetRequest : NewBudgetRequest
    ) : Response<BudgetResponse?>


}