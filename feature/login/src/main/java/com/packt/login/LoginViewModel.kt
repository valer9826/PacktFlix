package com.packt.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    var loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {}
}