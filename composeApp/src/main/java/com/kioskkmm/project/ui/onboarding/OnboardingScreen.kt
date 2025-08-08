package com.kioskkmm.project.ui.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shared.di.SharedModule
import com.example.shared.navigation.AppRoute
import com.kioskkmm.project.R
import com.kioskkmm.project.ui.components.PageIndicator
import kotlinx.coroutines.launch

data class OnboardingPage(
    val imageResId: Int,
    val title: String,
    val description: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val pages = listOf(
        OnboardingPage(R.drawable.ic_launcher_foreground, "Welcome", "Discover amazing features."),
        OnboardingPage(R.drawable.ic_launcher_foreground, "Explore", "Find what you need easily."),
        OnboardingPage(R.drawable.ic_launcher_foreground, "Get Started", "Join us today!")
    )
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    val authUseCases = SharedModule.provideAuthUseCases()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = {
                authUseCases.setFirstInstall(false)
                navController.navigate(AppRoute.Login.javaClass.name) {
                    popUpTo(AppRoute.Onboarding.javaClass.name) { inclusive = true }
                }
            }) {
                Text("Skip")
            }
        }

        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) {
            val page = pages[it]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = page.imageResId),
                    contentDescription = null,
                    modifier = Modifier.weight(0.5f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = page.title,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = page.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

        PageIndicator(pageCount = pages.size, currentPage = pagerState.currentPage)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = {
                if (pagerState.currentPage < pages.size - 1) {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                } else {
                    authUseCases.setFirstInstall(false)
                    navController.navigate(AppRoute.Login.javaClass.name) {
                        popUpTo(AppRoute.Onboarding.javaClass.name) { inclusive = true }
                    }
                }
            }) {
                Text(if (pagerState.currentPage == pages.size - 1) "Get Started" else "Next")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
