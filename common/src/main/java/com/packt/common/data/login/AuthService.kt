package com.packt.common.data.login

import com.packt.common.data.login.request.LoginRequest
import com.packt.common.data.login.response.AuthTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<AuthTokenResponse>

}