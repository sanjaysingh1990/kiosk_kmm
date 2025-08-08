package com.example.shared.prefs

import com.russhwolf.settings.Settings
import com.russhwolf.settings.NSUserDefaultsSettings
import platform.Foundation.NSUserDefaults

actual class SettingsFactory {
    actual fun createSettings(): Settings {
        return NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    }
}
