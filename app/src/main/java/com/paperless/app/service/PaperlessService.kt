package com.paperless.app.service

import com.paperless.app.datamodel.BudgetExpenseSummary
import com.paperless.app.datamodel.DashboardResponse
import com.paperless.app.datamodel.GoalSumaryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


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
}