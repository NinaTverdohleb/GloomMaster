package com.rumpilstilstkin.gloommaster.screens.teem.achievement.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloommaster.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract
import kotlinx.collections.immutable.ImmutableList

object AddAchievementDialogContract : OverlayContract<ImmutableList<AchievementWithName>, Unit> {
    @Composable
    override fun Content(
        input: ImmutableList<AchievementWithName>,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        AddAchievementDialogRoute(
            availableAchievements = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun AddAchievementDialogRoute(
    availableAchievements: ImmutableList<AchievementWithName>,
    close: (Unit?) -> Unit,
    viewModel: AddAchievementDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    AddAchievementDialog(
        availableAchievements = availableAchievements,
        onSelect = { viewModel.onAction(AddAchievementDialogAction.AddAchievement(it)) },
    )
}
