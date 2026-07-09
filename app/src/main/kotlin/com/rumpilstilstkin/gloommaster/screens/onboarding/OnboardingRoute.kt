package com.rumpilstilstkin.gloommaster.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloommaster.screens.core.LaunchedScreenEffect

@Composable
fun OnboardingRoute(
    navController: NavHostController,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    OnboardingScreen(
        pages = defaultOnboardingPages,
        onFinish = { viewModel.onAction(OnboardingAction.Finish) },
    )

    LaunchedScreenEffect(
        effects = viewModel.screenEvents,
        navController = navController,
    )
}
