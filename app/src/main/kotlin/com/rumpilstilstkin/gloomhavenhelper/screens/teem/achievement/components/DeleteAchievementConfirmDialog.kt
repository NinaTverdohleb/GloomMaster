package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun DeleteAchievementConfirmDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    GloomAlertDialog(
        title = stringResource(R.string.delete_achievement_title),
        onDismissRequest = onDismiss,
        onConfirmRequest = null,
        onNeutralRequest = onDismiss,
        onNegativeRequest = onConfirm,
        negativeText = stringResource(R.string.delete),
        content = {
            Text(
                text = stringResource(R.string.delete_achievement_warning),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    )
}

@Preview
@Composable
private fun DeleteAchievementConfirmDialogPreview() {
    GloomhavenMasterTheme {
        DeleteAchievementConfirmDialog(
            onDismiss = {},
            onConfirm = {},
        )
    }
}