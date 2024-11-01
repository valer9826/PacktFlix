package com.packt.login.domain.use_case

import com.packt.common.domain.LoginRepository

interface DoLoginUseCase {
    suspend fun doLoginWithCredentials(email: String, password: String): Result<Unit>
}

class DoLoginUseCaseImpl(
    private val loginRepository: LoginRepository
) : DoLoginUseCase {

    override suspend fun doLoginWithCredentials(email: String, password: String): Result<Unit> {
        return loginRepository.loginWithCredentials(email = email, password = password)
    }
}