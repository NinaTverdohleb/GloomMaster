package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AddAchievementDialog(
    availableAchievements: ImmutableList<Achievement>,
    onDismiss: () -> Unit,
    onSelect: (Achievement) -> Unit,
) {
    GloomAlertDialog(
        title = "Добавить достижение",
        onDismissRequest = onDismiss,
        onConfirmRequest = null,
        onNeutralRequest = onDismiss,
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = availableAchievements,
                    key = { it.name }
                ) { achievement ->
                    GloomCard(
                        modifier = Modifier.clickable { onSelect(achievement) }
                    ) {
                        Text(
                            text = achievement.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = 8.dp),
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun AddAchievementDialogPreview() {
    GloomhavenMasterTheme {
        AddAchievementDialog(
            availableAchievements = persistentListOf(
                Achievement.fixture("Первые шаги"),
                Achievement.fixture("Планы Джексеры"),
                Achievement.fixture("Древняя технология", 3),
            ),
            onDismiss = {},
            onSelect = {},
        )
    }

}