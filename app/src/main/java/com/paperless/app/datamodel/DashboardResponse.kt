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

@JsonClass(generateAdapter = true)
data class GoalSummary(
    @Json(name = "goalSeq") val goalSeq : Long,
    @Json(name = "goalName") val goalName : String,
    @Json(name = "goalDesc") val goalDesc : String,
    @Json(name = "goalStatus") val goalStatus : String,
    @Json(name = "totalAmount") val totalAmount : Float,
    @Json(name = "percentageComplete") val percentageComplete: Float
)

@JsonClass(generateAdapter = true)
data class GoalSummaryData(
    @Json(name = "totalGoalAmount") val totalGoalAmount : Float,
    @Json(name = "goalPaid") val goalPaid : Float,
    @Json(name = "goalFor") val goalFor : String,
    @Json(name = "listGoals") val listGoals : List<GoalSummary>?
)

@JsonClass(generateAdapter = true)
data class GoalSumaryResponse(
    @Json(name = "data") val goalSummary : GoalSummaryData,
    @Json(name = "error") val error: Error?
)

// monthly expense details

@JsonClass(generateAdapter = true)
data class MonthlyExpenseResponse(
    @Json(name = "data") val expenseDetails : MonthlyExpenseDetails,
    @Json(name = "error") val error: Error?
)

@JsonClass(generateAdapter = true)
data class MonthlyExpenseDetails(
    @Json(name = "totalExpense") val totalExpense: Float,
    @Json(name = "totalBudget") val totalBudget: Float,
    @Json(name = "listExpenseCategory") var listExpenseCategory: List<ExpenseCategory>?,
    @Json(name = "listTopSpending") var listTopSpending: List<TopSpending>?
)
@JsonClass(generateAdapter = true)
data class ExpenseCategory(
    @Json(name = "categoryId") val categoryId : Long,
    @Json(name = "categoryName") var categoryName : String,
    @Json(name = "amount") val amount : Float
)
@JsonClass(generateAdapter = true)
data class TopSpending(
    @Json(name = "expenseId") val expenseId: Long,
    @Json(name = "expenseName") val expenseName : String,
    @Json(name = "amount") val amount: Float,
    @Json(name = "icon") val icon : String?,
    @Json(name = "date") val date : Long,
    @Json(name = "categoryName") val categoryName: String
)

// chart summary / statistics

@JsonClass(generateAdapter = true)
data class ChartRequest(
    @Json(name = "month") val month : Int,
    @Json(name = "year") val year : Int,
    @Json(name = "chartType") val chartType : String, //weekly, monthly, yearly
    @Json(name = "userId") val userId: Long,
    @Json(name = "week") val week : Int
)

@JsonClass(generateAdapter = true)
data class ChartResponse(
    @Json(name = "data") val chartSummary : ChartSummary,
    @Json(name = "error") val error: Error?
)

@JsonClass(generateAdapter = true)
data class ChartSummary(
    @Json(name = "month") val month : Int,
    @Json(name = "year") val year : Int,
    @Json(name = "chartType") val chartType : String, //monthly, weekly, yearly
    @Json(name = "categoryList") var categoryList : List<ExpenseCategory>?, //pie chart for the chart type,
    @Json(name = "chartDataList") var chartDataList : List<ChartData>?
)

@JsonClass(generateAdapter = true)
data class ChartData(
    @Json(name = "xdata") val xData : String,
    @Json(name = "yexpense") val yExpense : Float = 0f,
    @Json(name = "yincome") val yIncome : Float = 0f,
    @Json(name = "ybudget") val yBudget : Float = 0f
)

//add new transaction
@JsonClass(generateAdapter = true)
data class NewTransactionResponse(
    @Json(name = "data") val transactionId : Long,
    @Json(name = "error") val error: Error?
)

@JsonClass(generateAdapter = true)
data class NewTransactionRequest(
    @Json(name = "userId") val userId : Long,
    @Json(name = "amount") val amount : Float,
    @Json(name = "transactionDate") val transactionDate : Long?,
    @Json(name = "transactionType") val transactionTypeId : Long,
    @Json(name = "transactionTypeLevel") val transactionTypeLevel : Int,
    @Json(name = "transactionMode") val transactionMode : String, //.debit , credit
    @Json(name = "transactionSource") val transactionSource : String, //sms manual
    @Json(name = "paidBy") val paidBy : String, // cash, online
    @Json(name = "isSourceCorrect") val isSourceCorrect : Boolean,
    @Json(name = "transactionTitle") val transactionTitle : String,
    @Json(name = "budgetId") val budgetId : Int = 0
)
@JsonClass(generateAdapter = true)
data class RecentTransactionResp(
    @Json(name = "data") val transactionList : List<Transaction>?,
    @Json(name = "error") val error: Error?
)

//new budget
@JsonClass(generateAdapter = true)
data class NewBudgetRequest(
    @Json(name = "budgetSeq") val budgetSeq : Long?,
    @Json(name = "userId") val userId: Long,
    @Json(name = "budgetFor") val budgetFor : String, //mm_yyyy
    @Json(name = "budgetTitle") val budgetTitle : String,
    @Json(name = "budgetAmount") val budgetAmount : Float,
    @Json(name = "dateAdded") val dateAdded : Long?,
    @Json(name = "transactionType") val transactionType : Long,
    @Json(name = "transactionTypeLevel") val transactionTypeLevel : Int
)

@JsonClass(generateAdapter = true)
data class BudgetResponse(
    @Json(name = "data") val budgetResp : NewBudgetResp?,
    @Json(name = "error") val error: Error?
)
@JsonClass(generateAdapter = true)
data class NewBudgetResp(
    @Json(name = "totalBudget") val totalBudget : Float,
    @Json(name = "newAmount") val newAmount : Float,
    @Json(name = "budgetSeq") val budgetSeq : Long
)


