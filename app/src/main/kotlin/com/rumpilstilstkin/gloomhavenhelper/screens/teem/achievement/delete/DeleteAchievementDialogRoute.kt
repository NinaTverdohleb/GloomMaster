package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.delete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract

object DeleteAchievementDialogContract : OverlayContract<AchievementWithName, Unit> {

    @Composable
    override fun Content(input: AchievementWithName, onDismissWithResult: (Unit?) -> Unit) {
        DeleteAchievementDialogRoute(
            achievement = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun DeleteAchievementDialogRoute(
    achievement: AchievementWithName,
    close: (Unit?) -> Unit,
    viewModel: DeleteAchievementDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    DeleteAchievementDialog(
        achievement = achievement,
        close = { close(null) },
        delete = { viewModel.onAction(DeleteAchievementDialogAction.DeleteAchievement(achievement.slug)) },
    )
}
