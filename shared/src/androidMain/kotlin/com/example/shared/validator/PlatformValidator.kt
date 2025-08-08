package com.example.shared.validator

actual fun isValidEmailPlatform(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
