package com.kioskkmm.project.platform

import android.content.Context
import com.example.shared.prefs.Preferences
import com.example.shared.prefs.SettingsFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

class AndroidPreferencesAdapter(private val context: Context) : Preferences {
    private val settings: Settings = SharedPreferencesSettings(context.getSharedPreferences("kmm_onboarding_prefs", Context.MODE_PRIVATE))

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
