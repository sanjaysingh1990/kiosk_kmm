package com.kioskkmm.project.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shared.navigation.AppRoute
import com.kioskkmm.project.ui.components.CustomOutlinedTextField
import com.kioskkmm.project.ui.components.PrimaryButton
import com.kioskkmm.project.ui.components.SocialLoginButton

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    val authState by viewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        CustomOutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = "Email",
            leadingIcon = Icons.Default.Email,
            isError = authState.emailError != null,
            errorMessage = authState.emailError,
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = "Password",
            leadingIcon = Icons.Default.Lock,
            isError = authState.passwordError != null,
            errorMessage = authState.passwordError,
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = "Login",
            onClick = { viewModel.login() },
            enabled = !authState.isLoading && authState.emailError == null && authState.passwordError == null
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Don't have an account?")
            TextButton(onClick = { navController.navigate(AppRoute.Signup.javaClass.name) }) {
                Text("Signup")
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Text("Or login with:", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SocialLoginButton(Icons.Default.Email) { /* Apple Login */ }
            SocialLoginButton(Icons.Default.Email) { /* Google Login */ }
            SocialLoginButton(Icons.Default.Email) { /* Facebook Login */ }
        }
    }
}
