package com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.RightItemChecker
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterName
import com.rumpilstilstkin.gloommaster.testtags.screens.scenario.play.monsters.AddScenarioMonstersDialogTestTags
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonsterListDialog(
    monsters: ImmutableList<MonsterName>,
    selectedIds: ImmutableList<String>,
    toggleMonster: (String) -> Unit,
    selectMonsters: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(
                items = monsters,
                key = { _, monster -> monster.slug },
            )
            { index, monster ->
                GloomListFilledItem(
                    modifier =
                        Modifier
                            .testTag(AddScenarioMonstersDialogTestTags.monster(index)),
                    title = monster.name,
                    rightComponent = {
                        RightItemChecker(
                            checked = selectedIds.contains(monster.slug),
                        ) {
                            toggleMonster(monster.slug)
                        }
                    },
                )
            }
        }

        if (monsters.isNotEmpty()) {
            GloomOutlineButton(
                modifier =
                    Modifier
                        .testTag(AddScenarioMonstersDialogTestTags.ADD_BUTTON)
                        .fillMaxWidth(),
                icon = AppIcon.Check,
                text = stringResource(R.string.select_enemies),
                onClick = selectMonsters,
                enabled = selectedIds.isNotEmpty(),
            )
        }
    }
}

@Preview
@Composable
private fun MonsterListDialogPreview() {
    GloomhavenMasterTheme {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            MonsterListDialog(
                monsters =
                    persistentListOf(
                        MonsterName("1", "Skeleton"),
                        MonsterName("2", "Zombie"),
                    ),
                selectedIds = persistentListOf("1"),
                toggleMonster = {},
                selectMonsters = {},
            )
        }
    }
}
