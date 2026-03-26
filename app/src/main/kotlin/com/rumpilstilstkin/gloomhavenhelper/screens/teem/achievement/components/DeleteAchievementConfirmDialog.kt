package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun DeleteAchievementConfirmDialog(
    achievementName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    GloomAlertDialog(
        title = "Удалить достижение?",
        onDismissRequest = onDismiss,
        onConfirmRequest = null,
        onNeutralRequest = onDismiss,
        onNegativeRequest = onConfirm,
        negativeText = "Удалить",
        content = {
            Text(
                text = "Вы уверены, что хотите удалить достижение \"$achievementName\"?",
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
            achievementName = "Первые шаги",
            onDismiss = {},
            onConfirm = {},
        )
    }
}