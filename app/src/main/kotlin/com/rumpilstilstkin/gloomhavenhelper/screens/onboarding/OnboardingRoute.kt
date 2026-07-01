package com.rumpilstilstkin.gloomhavenhelper.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.screens.core.LaunchedScreenEffect

@Composable
fun OnboardingRoute(
    navController: NavHostController,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val screenEffect by viewModel.screenEvents.collectAsStateWithLifecycle(initialValue = null)

    OnboardingScreen(
        pages = defaultOnboardingPages,
        onFinish = { viewModel.onAction(OnboardingAction.Finish) },
    )

    LaunchedScreenEffect(
        effect = screenEffect,
        navController = navController,
    )
}
