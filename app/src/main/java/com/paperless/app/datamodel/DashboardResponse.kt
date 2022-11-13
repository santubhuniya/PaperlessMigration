package com.paperless.app.datamodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DashboardResponse(
    @Json(name = "data") val transactionSummary: TransactionSummary?,
    @Json(name = "error") val error: Error?
)

@JsonClass(generateAdapter = true)
data class Error(
    @Json(name = "errorCode") val errorCode: Int,
    @Json(name = "errorMessage") val errorMessage: String
)

@JsonClass(generateAdapter = true)
data class TransactionSummary(
    @Json(name = "totalIncome") val totalIncome : Float,
    @Json(name = "totalExpense") val totalExpense : Float,
    @Json(name = "monthlyExpense") val monthlyExpense : Float,
    @Json(name = "monthlyBudget") var monthlyBudget : Float = 0.0F,
    @Json(name = "lastFiveTransaction") val lastFiveTransaction : List<Transaction>
)

@JsonClass(generateAdapter = true)
data class Transaction(
    @Json(name = "txnId") val txnId : Long,
    @Json(name = "txnTitle") val txnTitle : String,
    @Json(name = "txnAmount") val txnAmount : Float,
    @Json(name = "txnTypeName") val txnTypeName : String,
    @Json(name = "isExpense") val isExpense : Boolean,
    @Json(name = "txnDate") val txnDate : Long
)

@JsonClass(generateAdapter = true)
data class BudgetExpenseSummary(
    @Json(name = "data") val budgetSummary: List<BudgetSummary>?,
    @Json(name = "error") val error: Error?
)

@JsonClass(generateAdapter = true)
data class BudgetSummary(
    @Json(name = "expenseTypeId") val expenseTypeId : Long,
    @Json(name = "expenseTypeName") val expenseTypeName : String,
    @Json(name = "budgetId") var budgetId: Long?,
    @Json(name = "budgetName") var budgetName : String?,
    @Json(name = "expenseAmount") var expenseAmount : Float?,
    @Json(name = "budgetAmount") var budgetAmount: Float?,
    @Json(name = "monthYear") val monthYear : String
)
