package com.kioskkmm.project.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shared.di.SharedModule
import com.example.shared.state.AuthState
import com.example.shared.validator.Validators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authUseCases = SharedModule.provideAuthUseCases()

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var fullName by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    fun login() {
        _authState.value = _authState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            val emailValid = Validators.isValidEmail(email)
            val passwordValid = Validators.isValidPassword(password)

            if (!emailValid) {
                _authState.value = _authState.value.copy(emailError = "Invalid email format")
            }
            if (!passwordValid) {
                _authState.value = _authState.value.copy(passwordError = "Password must be at least 6 characters")
            }

            if (emailValid && passwordValid) {
                val result = authUseCases.login(email, password)
                result.onSuccess {
                    _authState.value = _authState.value.copy(loginSuccess = true, isLoading = false)
                }.onFailure {
                    _authState.value = _authState.value.copy(error = it.message, isLoading = false)
                }
            } else {
                _authState.value = _authState.value.copy(isLoading = false)
            }
        }
    }

    fun signup() {
        _authState.value = _authState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            val fullNameValid = Validators.isValidFullName(fullName)
            val emailValid = Validators.isValidEmail(email)
            val passwordValid = Validators.isValidPassword(password)
            val confirmPasswordValid = password == confirmPassword

            if (!fullNameValid) {
                _authState.value = _authState.value.copy(fullNameError = "Full name must be longer than 2 characters")
            }
            if (!emailValid) {
                _authState.value = _authState.value.copy(emailError = "Invalid email format")
            }
            if (!passwordValid) {
                _authState.value = _authState.value.copy(passwordError = "Password must be at least 6 characters")
            }
            if (!confirmPasswordValid) {
                _authState.value = _authState.value.copy(confirmPasswordError = "Passwords do not match")
            }

            if (fullNameValid && emailValid && passwordValid && confirmPasswordValid) {
                val result = authUseCases.signup(fullName, email, password)
                result.onSuccess {
                    _authState.value = _authState.value.copy(signupSuccess = true, isLoading = false)
                }.onFailure {
                    _authState.value = _authState.value.copy(error = it.message, isLoading = false)
                }
            } else {
                _authState.value = _authState.value.copy(isLoading = false)
            }
        }
    }
}
