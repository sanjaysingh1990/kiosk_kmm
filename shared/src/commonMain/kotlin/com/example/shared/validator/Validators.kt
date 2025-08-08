package com.example.shared.validator

object Validators {
    fun isValidEmail(email: String): Boolean {
        return isValidEmailPlatform(email)
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun isValidFullName(fullName: String): Boolean {
        return fullName.length > 2
    }
}
