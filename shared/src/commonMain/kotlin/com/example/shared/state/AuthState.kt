package com.example.shared.state

data class AuthState(
    val emailError: String? = null,
    val passwordError: String? = null,
    val fullNameError: String? = null,
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val signupSuccess: Boolean = false,
    val error: String? = null
)
