package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.global

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.AchievementsAction
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.AchievementsScreen

@Composable
fun GlobalAchievementsRoute(
    navController: NavHostController,
    viewModel: GlobalAchievementsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController,
            )
        }
    }

    AchievementsScreen(
        title = stringResource(R.string.global_achievements),
        uiState = uiState,
        showAddDialog = { viewModel.onAction(AchievementsAction.ShowAddDialog) },
        dismissAddDialog = { viewModel.onAction(AchievementsAction.DismissAddDialog) },
        addAchievement = { viewModel.onAction(AchievementsAction.AddAchievement(it)) },
        showDeleteDialog = { viewModel.onAction(AchievementsAction.ShowDeleteConfirmDialog(it)) },
        dismissDeleteDialog = { viewModel.onAction(AchievementsAction.DismissDeleteConfirmDialog) },
        confirmDelete = { viewModel.onAction(AchievementsAction.ConfirmDelete) },
        back = { viewModel.onAction(AchievementsAction.Back) },
        updateAchievement = { value, achievement ->
            viewModel.onAction(AchievementsAction.UpdateAchievement(value, achievement))
        },
    )
}
