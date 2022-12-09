package com.paperless.app.service

import com.paperless.app.datamodel.LoginReponseData
import com.paperless.app.datamodel.LoginRequest
import com.paperless.app.datamodel.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("paperless/login")
    suspend fun doLogin(@Body loginRequest : LoginRequest)
    : Response<LoginReponseData?>
}