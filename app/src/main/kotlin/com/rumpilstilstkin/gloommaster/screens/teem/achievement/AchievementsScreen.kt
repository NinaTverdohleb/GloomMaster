package com.rumpilstilstkin.gloommaster.screens.teem.achievement

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomFab
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomItemActionIcon
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomSwipeableListItem
import com.rumpilstilstkin.gloommaster.designsystem.components.toolbar.GloomToolbar
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloommaster.screens.teem.achievement.components.AchievementItem
import com.rumpilstilstkin.gloommaster.screens.teem.achievement.components.EmptyAchievements
import com.rumpilstilstkin.gloommaster.testtags.screens.teem.achievement.AchievementsScreenTestTags
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun AchievementsScreen(
    uiState: AchievementsStateUi,
    title: String,
    addAchievement: () -> Unit,
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
        if (uiState.availableAchievements.isNotEmpty()) {
            GloomFab(
                icon = AppIcon.Plus,
                onClick = addAchievement,
                modifier = Modifier.testTag(AchievementsScreenTestTags.ADD_FAB),
            )
        }
    },
) { innerPadding ->
    Column(
        modifier =
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
    ) {
        if (uiState.achievements.isEmpty()) {
            EmptyAchievements()
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
                                onClick = { deleteAchievement(achievement) },
                            )
                        },
                        item = {
                            AchievementItem(
                                achievement = achievement,
                                changeValue = { updateAchievement(it, achievement) },
                            )
                        },
                    )
                }
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
            addAchievement = {},
            deleteAchievement = {},
            back = {},
            updateAchievement = { _, _ -> },
        )
    }
}
