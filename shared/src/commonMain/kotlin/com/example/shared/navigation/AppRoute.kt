package com.example.shared.navigation

sealed class AppRoute {
    object Splash : AppRoute()
    object Onboarding : AppRoute()
    object Login : AppRoute()
    object Signup : AppRoute()
    object Home : AppRoute()
}
