package com.example.shared.prefs

import com.russhwolf.settings.Settings

expect class SettingsFactory {
    fun createSettings(): Settings
}
