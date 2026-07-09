package com.rumpilstilstkin.gloommaster.screens.teem.achievement.global

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.screens.core.LaunchedScreenEffect
import com.rumpilstilstkin.gloommaster.screens.teem.achievement.AchievementsAction
import com.rumpilstilstkin.gloommaster.screens.teem.achievement.AchievementsScreen

@Composable
fun GlobalAchievementsRoute(
    navController: NavHostController,
    viewModel: GlobalAchievementsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AchievementsScreen(
        title = stringResource(R.string.global_achievements),
        uiState = uiState,
        addAchievement = { viewModel.onAction(AchievementsAction.ShowAddDialog) },
        deleteAchievement = { viewModel.onAction(AchievementsAction.DeleteAchievement(it)) },
        back = { viewModel.onAction(AchievementsAction.Back) },
        updateAchievement = { value, achievement ->
            viewModel.onAction(AchievementsAction.UpdateAchievement(value, achievement))
        },
    )

    LaunchedScreenEffect(
        effects = viewModel.screenEvents,
        navController = navController,
    )
}
