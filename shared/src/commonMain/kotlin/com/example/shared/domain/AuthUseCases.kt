package com.example.shared.domain

import com.example.shared.data.UserRepository
import com.example.shared.model.User
import com.example.shared.prefs.Preferences

class AuthUseCases(
    private val userRepository: UserRepository,
    private val preferences: Preferences
) {
    suspend fun login(email: String, password: String): Result<User> {
        return userRepository.login(email, password)
    }

    suspend fun signup(fullName: String, email: String, password: String): Result<User> {
        return userRepository.signup(fullName, email, password)
    }

    fun isFirstInstall(): Boolean {
        return preferences.getBoolean("firstTimeInstall", true)
    }

    fun setFirstInstall(flag: Boolean) {
        preferences.putBoolean("firstTimeInstall", flag)
    }
}
