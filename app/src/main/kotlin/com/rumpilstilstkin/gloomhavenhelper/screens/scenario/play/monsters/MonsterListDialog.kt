package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.RightItemChecker
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonsterListDialog(
    monsters: ImmutableList<MonsterItem>,
    selectedIds: ImmutableList<String>,
    toggleMonster: (String) -> Unit,
    selectMonsters: () -> Unit,
    addNewMonsters: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(items = monsters, key = { it.slug }) { monster ->
                GloomListFilledItem(
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

        GloomOutlineButton(
            modifier = Modifier.fillMaxWidth(),
            icon = AppIcon.Plus,
            text = stringResource(R.string.add_new_enemies),
            onClick = addNewMonsters,
        )

        GloomOutlineButton(
            modifier = Modifier.fillMaxWidth(),
            icon = AppIcon.Check,
            text = stringResource(R.string.select_enemies),
            onClick = selectMonsters,
            enabled = selectedIds.isNotEmpty(),
        )
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
                        MonsterItem.fixture("1", "Skeleton"),
                        MonsterItem.fixture("2", "Zombie"),
                    ),
                selectedIds = persistentListOf("1"),
                toggleMonster = {},
                selectMonsters = {},
                addNewMonsters = {},
            )
        }
    }
}
