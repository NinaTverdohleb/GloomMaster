package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.delete

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.dialogs.ConfirmationDeleteDialog
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName

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
