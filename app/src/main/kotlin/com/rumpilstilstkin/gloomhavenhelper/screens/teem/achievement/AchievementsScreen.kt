package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomFab
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomItemActionIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomSwipeableListItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.toolbar.GloomToolbar
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.components.AchievementItem
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.components.AddAchievementDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.components.EmptyAchievements
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun AchievementsScreen(
    uiState: AchievementsStateUi,
    title: String,
    showAddDialog: () -> Unit,
    dismissAddDialog: () -> Unit,
    addAchievement: (AchievementWithName) -> Unit,
    deleteAchievement: (AchievementWithName) -> Unit,
    back: () -> Unit,
    updateAchievement: (Int, AchievementWithName) -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbar(
            title = title,
            back = back,
        )
    },
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
        if(uiState.availableAchievements.isNotEmpty()) {
            GloomFab(
                icon = AppIcon.Plus,
                onClick = showAddDialog,
            )
        }
    }
) { innerPadding ->
    Column(
        modifier =
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
    ) {
        if (uiState.achievements.isEmpty()) {
            EmptyAchievements(
                modifier = Modifier.weight(1f),
            )
        } else {
            LazyColumn(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(
                    items = uiState.achievements,
                    key = { it.slug },
                ) { achievement ->
                    GloomSwipeableListItem(
                        menuContent = {
                            GloomItemActionIcon(
                                modifier = Modifier.fillMaxHeight(),
                                icon = AppIcon.Delete,
                                isError = true,
                                onClick = { deleteAchievement(achievement) }
                            )
                        },
                        item = {
                            AchievementItem(
                                achievement = achievement,
                                changeValue = { updateAchievement(it, achievement) },
                            )
                        }
                    )
                }
            }
        }
    }

    if (uiState.showAddDialog) {
        AddAchievementDialog(
            availableAchievements = uiState.availableAchievements,
            onDismiss = dismissAddDialog,
            onSelect = addAchievement,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun AchievementsScreenPreview() {
    GloomhavenMasterTheme {
        AchievementsScreen(
            title = "Party Achievements",
            uiState =
                AchievementsStateUi(
                    achievements =
                        persistentListOf(
                            AchievementWithName.fixture("First Steps"),
                            AchievementWithName.fixture("Jekserah's Plans"),
                            AchievementWithName.fixture("Ancient Technology", maxValue = 3),
                        ),
                    availableAchievements =
                        persistentListOf(
                            AchievementWithName.fixture("Treasure Map"),
                        ),
                ),
            showAddDialog = {},
            dismissAddDialog = {},
            addAchievement = {},
            deleteAchievement = {},
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
            deleteAchievement = {},
            back = {},
            updateAchievement = { _, _ -> },
        )
    }
}
