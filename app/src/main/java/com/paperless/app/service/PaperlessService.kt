package com.paperless.app.service

import com.paperless.app.datamodel.DashboardResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface PaperlessService {
    @GET("transaction/summary/{userId}/{monthYear}")
    suspend fun getTransactionSummary(
        @Path("userId") userId : Long,
        @Path("monthYear") monthYear : String
    ) : Response<DashboardResponse?>
}