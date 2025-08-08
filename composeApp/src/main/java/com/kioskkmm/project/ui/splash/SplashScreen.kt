package com.kioskkmm.project.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shared.domain.AuthUseCases
import com.example.shared.navigation.AppRoute
import com.kioskkmm.project.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // TODO: Inject AuthUseCases
    val authUseCases = SharedModule.provideAuthUseCases()

    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds delay
        if (authUseCases.isFirstInstall()) {
            navController.navigate(AppRoute.Onboarding.javaClass.name) {
                popUpTo(AppRoute.Splash.javaClass.name) { inclusive = true }
            }
        } else {
            navController.navigate(AppRoute.Login.javaClass.name) {
                popUpTo(AppRoute.Splash.javaClass.name) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Placeholder for system logo
            contentDescription = "App Logo",
            modifier = Modifier.size(128.dp)
        )
        Text(
            text = "KMM Onboarding App",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
