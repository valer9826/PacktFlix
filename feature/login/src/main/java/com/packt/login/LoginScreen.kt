package com.packt.login

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState = loginViewModel.loginState.collectAsState().value

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
//    val focusRequester  = remember{ FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        color = Color.Black, modifier =
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus() // Quita el foco de todos los elementos
                    keyboardController?.hide() // Oculta el teclado
                })
            }
    ) {
        Column(
            horizontalAlignment =
            Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
        ) {
            if (loginState is LoginState.Error) {
                Text(
                    text = loginState.message ?: "Unknown error",
                    color = Color.Red,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            }
            Text(
                text = "PACKTFLIX",
                color = Color.Red,
                fontSize = 36.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            OutlinedTextField(
                maxLines = 1,
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor =
                    Color.Transparent,
                    focusedTextColor = Color.White,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                maxLines = 1,
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    focusedContainerColor =
                    Color.Transparent,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        loginViewModel.login(
                            email, password
                        )
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    loginViewModel.login(
                        email,
                        password
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )
            ) {
                Text("Sign In", color = Color.White)
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (loginState is LoginState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                // Navigate to next screen or show success
            }

            is LoginState.Error -> {
                errorMessage = loginState.message ?: "An error occurred"
            }

            else -> Unit
        }
    }
}