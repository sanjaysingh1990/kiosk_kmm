package com.example.shared.navigation

sealed class AppRoute {
    data object Splash : AppRoute()
    data object Onboarding : AppRoute()
    data object Login : AppRoute()
    data object Signup : AppRoute()
    data object Home : AppRoute()
}
