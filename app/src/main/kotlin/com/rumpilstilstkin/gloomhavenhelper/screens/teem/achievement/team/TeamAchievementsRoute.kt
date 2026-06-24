package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.team

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.core.LaunchedScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.AchievementsAction
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.AchievementsScreen

@Composable
fun TeamAchievementsRoute(
    navController: NavHostController,
    viewModel: TeamAchievementsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val screenEffect by viewModel.screenEvents.collectAsStateWithLifecycle(initialValue = null)

    AchievementsScreen(
        title = stringResource(R.string.team_achievements),
        uiState = uiState,
        showAddDialog = { viewModel.onAction(AchievementsAction.ShowAddDialog) },
        dismissAddDialog = { viewModel.onAction(AchievementsAction.DismissAddDialog) },
        addAchievement = { viewModel.onAction(AchievementsAction.AddAchievement(it)) },
        deleteAchievement = { viewModel.onAction(AchievementsAction.DeleteAchievement(it)) },
        back = { viewModel.onAction(AchievementsAction.Back) },
        updateAchievement = { value, achievement ->
            viewModel.onAction(AchievementsAction.UpdateAchievement(value, achievement))
        },
    )

    LaunchedScreenEffect(
        effect = screenEffect,
        navController = navController,
    )
}
