package com.example.shared.data

import com.example.shared.model.User

interface UserRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun signup(fullName: String, email: String, password: String): Result<User>
}
