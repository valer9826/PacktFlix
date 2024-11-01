package com.packt.login.data

import com.packt.login.data.request.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<com.packt.login.domain.model.AuthToken>

}