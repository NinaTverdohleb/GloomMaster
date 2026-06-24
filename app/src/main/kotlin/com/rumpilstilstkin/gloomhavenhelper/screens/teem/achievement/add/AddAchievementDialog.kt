package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomListOutlineItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AddAchievementDialog(
    availableAchievements: ImmutableList<AchievementWithName>,
    onSelect: (AchievementWithName) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = availableAchievements,
            key = { it.slug },
        ) { achievement ->
            GloomListOutlineItem(
                title = achievement.name,
                onClick = { onSelect(achievement) }
            )
        }
    }
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
            onSelect = {},
        )
    }
}
