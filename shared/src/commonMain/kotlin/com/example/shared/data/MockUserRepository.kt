package com.example.shared.data

import com.example.shared.model.User
import kotlinx.coroutines.delay

class MockUserRepository : UserRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        delay(1000) // Simulate network delay
        return if (email.isNotEmpty() && password.isNotEmpty()) {
            Result.success(User(email, "Mock User"))
        } else {
            Result.failure(Exception("Invalid credentials"))
        }
    }

    override suspend fun signup(fullName: String, email: String, password: String): Result<User> {
        delay(1000) // Simulate network delay
        return if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            Result.success(User(email, fullName))
        } else {
            Result.failure(Exception("Invalid signup data"))
        }
    }
}
