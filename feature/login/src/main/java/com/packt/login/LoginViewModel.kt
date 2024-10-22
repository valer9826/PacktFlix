package com.packt.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.common.domain.login.use_case.DoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: DoLoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    var loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = loginUseCase.invoke(email, password)
            _loginState.value = LoginState.Loading
            _loginState.value = when {
                result.isFailure -> LoginState.Error(
                    result.exceptionOrNull()?.message
                )

                else -> LoginState.Success
            }
        }
    }
}