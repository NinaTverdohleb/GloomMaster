package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.components.AddAchievementDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.components.DeleteAchievementConfirmDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.components.EmptyAchievements
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomSize
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomToolbarTitle
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NumberPicker
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun AchievementsScreen(
    uiState: AchievementsStateUi,
    title: String,
    showAddDialog: () -> Unit,
    dismissAddDialog: () -> Unit,
    addAchievement: (Achievement) -> Unit,
    showDeleteDialog: (Achievement) -> Unit,
    dismissDeleteDialog: () -> Unit,
    confirmDelete: () -> Unit,
    back: () -> Unit,
    updateAchievement: (Int, Achievement) -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbarTitle(
            title = title,
            back = back,
        )
    },
) { innerPadding ->
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        if (uiState.achievements.isEmpty()) {
            EmptyAchievements(
                modifier = Modifier.weight(1f)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = uiState.achievements,
                    key = { it.name }
                ) { achievement ->
                    AchievementItem(
                        achievement = achievement,
                        delete = { showDeleteDialog(achievement) },
                        changeValue = { updateAchievement(it, achievement) }
                    )
                }
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = showAddDialog,
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(
                text = stringResource(R.string.add_achievement_button),
                fontSize = 16.sp
            )
        }
    }

    if (uiState.showAddDialog) {
        AddAchievementDialog(
            availableAchievements = uiState.availableAchievements,
            onDismiss = dismissAddDialog,
            onSelect = addAchievement,
        )
    }

    uiState.achievementToDelete?.let { achievement ->
        DeleteAchievementConfirmDialog(
            achievementName = achievement.displayName,
            onDismiss = dismissDeleteDialog,
            onConfirm = confirmDelete,
        )
    }
}

@Composable
private fun AchievementItem(
    achievement: Achievement,
    delete: () -> Unit,
    changeValue: (Int) -> Unit,
) {
    GloomCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = achievement.displayName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (achievement.maxValue > 1) {
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    NumberPicker(
                        value = achievement.value,
                        intRange = 1..achievement.maxValue,
                        size = GloomSize.S,
                        onValueChange = changeValue,
                    )
                }
            }
            IconButton(onClick = delete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun AchievementsScreenPreview() {
    GloomhavenMasterTheme {
        AchievementsScreen(
            title = "Party Achievements",
            uiState = AchievementsStateUi(
                achievements = persistentListOf(
                    Achievement.fixture("First Steps"),
                    Achievement.fixture("Jekserah's Plans"),
                    Achievement.fixture("Ancient Technology", maxValue = 3),
                ),
                availableAchievements = persistentListOf(
                    Achievement.fixture("Treasure Map"),
                ),
            ),
            showAddDialog = {},
            dismissAddDialog = {},
            addAchievement = {},
            showDeleteDialog = {},
            dismissDeleteDialog = {},
            confirmDelete = {},
            back = {},
            updateAchievement = { _, _ -> },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun AchievementsScreenEmptyPreview() {
    GloomhavenMasterTheme {
        AchievementsScreen(
            title = "Party Achievements",
            uiState = AchievementsStateUi(),
            showAddDialog = {},
            dismissAddDialog = {},
            addAchievement = {},
            showDeleteDialog = {},
            dismissDeleteDialog = {},
            confirmDelete = {},
            back = {},
            updateAchievement = { _, _ -> },
        )
    }
}
