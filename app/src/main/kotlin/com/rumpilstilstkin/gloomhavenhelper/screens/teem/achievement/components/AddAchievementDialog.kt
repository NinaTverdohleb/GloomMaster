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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AddAchievementDialog(
    availableAchievements: ImmutableList<AchievementWithName>,
    onDismiss: () -> Unit,
    onSelect: (AchievementWithName) -> Unit,
) {
    GloomAlertDialog(
        title = stringResource(R.string.add_achievement_title),
        onDismissRequest = onDismiss,
        onConfirmRequest = null,
        onNeutralRequest = onDismiss,
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = availableAchievements,
                    key = { it.slug },
                ) { achievement ->
                    GloomCard(
                        modifier = Modifier.clickable { onSelect(achievement) },
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
        },
    )
}

@Preview
@Composable
private fun AddAchievementDialogPreview() {
    GloomhavenMasterTheme {
        AddAchievementDialog(
            availableAchievements =
                persistentListOf(
                    AchievementWithName.fixture("First Steps"),
                    AchievementWithName.fixture("Jekserah's Plans"),
                    AchievementWithName.fixture("Ancient Technology"),
                ),
            onDismiss = {},
            onSelect = {},
        )
    }
}
