package com.example.shared.di

import com.example.shared.data.MockUserRepository
import com.example.shared.data.UserRepository
import com.example.shared.domain.AuthUseCases
import com.example.shared.prefs.MppSettingsPreferences
import com.example.shared.prefs.Preferences
import com.example.shared.prefs.SettingsFactory

object SharedModule {

    private lateinit var settingsFactory: SettingsFactory

    fun init(settingsFactory: SettingsFactory) {
        SharedModule.settingsFactory = settingsFactory
    }

    fun provideUserRepository(): UserRepository {
        return MockUserRepository()
    }

    fun providePreferences(): Preferences {
        return MppSettingsPreferences(settingsFactory.createSettings())
    }

    fun provideAuthUseCases(): AuthUseCases {
        return AuthUseCases(provideUserRepository(), providePreferences())
    }
}