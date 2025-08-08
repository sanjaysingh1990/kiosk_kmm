package com.example.shared.validator

actual fun isValidEmailPlatform(email: String): Boolean {
    // Basic email regex for iOS
    val emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
    return emailRegex.matches(email)
}

