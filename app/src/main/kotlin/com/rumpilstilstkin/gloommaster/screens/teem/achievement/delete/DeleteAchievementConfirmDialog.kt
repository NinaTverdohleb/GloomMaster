package com.rumpilstilstkin.gloommaster.screens.teem.achievement.delete

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.dialogs.ConfirmationDeleteDialog
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.AchievementWithName

@Composable
fun DeleteAchievementDialog(
    achievement: AchievementWithName,
    delete: () -> Unit,
    close: () -> Unit,
) {
    ConfirmationDeleteDialog(
        title = stringResource(R.string.delete_achievement_title),
        description =
            stringResource(
                R.string.delete_achivment_dialog_description,
                achievement.name,
            ),
        onDeleteConfirm = delete,
        onClose = close,
    )
}

@Preview
@Composable
private fun DeleteAchievementConfirmDialogPreview() {
    GloomhavenMasterTheme {
        DeleteAchievementDialog(
            achievement = AchievementWithName.fixture(),
            delete = {},
            close = {},
        )
    }
}
