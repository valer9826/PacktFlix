package com.packt.common.domain.login.use_case

import javax.inject.Inject

class DoLoginUseCase @Inject constructor() {

    suspend operator fun invoke(email: String, password: String){

    }
}