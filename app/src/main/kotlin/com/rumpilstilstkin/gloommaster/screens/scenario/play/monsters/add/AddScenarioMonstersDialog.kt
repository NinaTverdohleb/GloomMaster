package com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.RightItemChecker
import com.rumpilstilstkin.gloommaster.designsystem.components.text.GloomOutlinedTextSearchField
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterName
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

@Composable
internal fun AddScenarioMonstersDialog(
    monsters: ImmutableList<MonsterName>,
    selectedSlugs: ImmutableSet<String>,
    searchText: String,
    changeSearchText: (String) -> Unit,
    toggleMonster: (String) -> Unit,
    addMonsters: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        GloomOutlinedTextSearchField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            onValueChange = changeSearchText,
            placeholder = stringResource(R.string.search_monster_hint),
        )
        LazyColumn(
            modifier = Modifier.weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            itemsIndexed(
                items = monsters,
                key = { _, monster -> monster.slug },
            ) { index, monster ->
                GloomListFilledItem(
                    title = monster.name,
                    rightComponent = {
                        RightItemChecker(
                            checked = selectedSlugs.contains(monster.slug),
                        ) {
                            toggleMonster(monster.slug)
                        }
                    },
                )
            }
        }

        GloomOutlineButton(
            icon = AppIcon.Check,
            text = stringResource(R.string.add),
            onClick = addMonsters,
            enabled = selectedSlugs.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun AddScenarioMonstersDialogPreview() {
    GloomhavenMasterTheme {
        AddScenarioMonstersDialog(
            monsters =
                persistentListOf(
                    MonsterName("1", "Bandit Archer"),
                    MonsterName("2", "Living Spirit"),
                ),
            selectedSlugs = persistentSetOf("1"),
            searchText = "",
            changeSearchText = {},
            toggleMonster = {},
            addMonsters = {},
        )
    }
}
