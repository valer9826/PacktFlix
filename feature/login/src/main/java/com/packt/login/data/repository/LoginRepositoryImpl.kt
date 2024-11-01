package com.packt.login.data.repository

import com.packt.common.domain.LoginRepository
import com.packt.login.data.datasource.local.LoginLocalDataSource
import com.packt.login.data.datasource.remote.LoginRemoteDataSource
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val localDataSource: LoginLocalDataSource,
    private val remoteDataSource: LoginRemoteDataSource
): LoginRepository {

    override suspend fun getToken(): Result<String> {
        return localDataSource.getAuthToken()
    }

    override suspend fun loginWithCredentials(email: String, password: String): Result<Unit> {
        return remoteDataSource.login(email, password)
            .fold(
                onSuccess = {
                    localDataSource.saveAuthToken(it)
                    Result.success(Unit)
                },
                onFailure = {
                    Result.failure(it)
                }
            )

    }

}