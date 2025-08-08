package com.kioskkmm.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shared.navigation.AppRoute
import com.kioskkmm.project.ui.splash.SplashScreen
import com.example.shared.di.SharedModule
import com.example.shared.prefs.SettingsFactory
import com.kioskkmm.project.ui.onboarding.OnboardingScreen
import com.kioskkmm.project.ui.auth.LoginScreen
import com.kioskkmm.project.ui.auth.SignupScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedModule.init(SettingsFactory(applicationContext))
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoute.Splash.javaClass.name) {
        composable(AppRoute.Splash.javaClass.name) {
            SplashScreen(navController = navController)
        }
        composable(AppRoute.Onboarding.javaClass.name) {
            OnboardingScreen(navController = navController)
        }
        composable(AppRoute.Login.javaClass.name) {
            LoginScreen(navController = navController)
        }
        composable(AppRoute.Signup.javaClass.name) {
            SignupScreen(navController = navController)
        }
        // TODO: Add other destinations: Home
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}
