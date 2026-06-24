package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomFab
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks.components.EmptyPerks
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PerkUI
import com.rumpilstilstkin.gloomhavenhelper.ui.perks.PerkActionItem
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CharacterPerkTabScreen(
    uiState: CharacterPerksScreenStateUi,
    addPerk: () -> Unit,
    deletePerk: (PerkUI) -> Unit,
) = Scaffold(
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
        GloomFab(
            icon = AppIcon.Plus,
            onClick = addPerk,
        )
    },
) { innerPadding ->
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
    ) {
        Text(
            text = stringResource(R.string.available_to_add_format, uiState.avaliablePerksCount),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        if (uiState.characterPerks.isEmpty()) {
            EmptyPerks(
                modifier = Modifier.weight(1f),
            )
        } else {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
            ) {
                items(
                    items = uiState.characterPerks,
                    key = { perk -> perk.id },
                ) { perk ->
                    PerkActionItem(
                        perk = perk,
                        delete = deletePerk
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CharacterPerkTabScreenPreview() {
    GloomhavenMasterTheme {
        CharacterPerkTabScreen(
            uiState =
                CharacterPerksScreenStateUi(
                    characterPerks =
                        listOf(
                            PerkUI(1, "Remove two cards #01"),
                            PerkUI(
                                2,
                                "Replace one card #01 with one card #03",
                            ),
                            PerkUI(3, "Add two cards #03"),
                        ).toImmutableList(),
                    avaliablePerksCount = 4,
                ),
            addPerk = {},
            deletePerk = {},
        )
    }
}

@Preview
@Composable
private fun CharacterPerkTabScreenEmptyPreview() {
    GloomhavenMasterTheme {
        CharacterPerkTabScreen(
            uiState = CharacterPerksScreenStateUi(avaliablePerksCount = 4),
            addPerk = {},
            deletePerk = {},
        )
    }
}
