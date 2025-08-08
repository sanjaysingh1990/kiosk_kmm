package com.example.shared.prefs

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class SettingsFactory(private val context: Context) {
    actual fun createSettings(): Settings {
        val delegate = context.getSharedPreferences("kmm_onboarding_prefs", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate)
    }
}
