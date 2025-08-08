package com.example.shared.prefs

import com.russhwolf.settings.Settings

class MppSettingsPreferences(private val settings: Settings) : Preferences {
    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return settings.getBoolean(key, defaultValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        settings.putBoolean(key, value)
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return settings.getStringOrNull(key) ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        settings.putString(key, value)
    }
}
