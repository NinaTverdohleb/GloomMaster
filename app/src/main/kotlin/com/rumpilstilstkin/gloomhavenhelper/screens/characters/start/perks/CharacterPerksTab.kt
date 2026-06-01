package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character.AddPerksDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PerkUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.text.TextWithImagesByCode

@Composable
fun CharacterPerkTabScreen(
    uiState: CharacterPerksScreenStateUi,
    addPerk: () -> Unit,
    closeAddPerkDialog: () -> Unit,
    deletePerk: (Int) -> Unit,
    addPerks: (List<Int>) -> Unit,
) {
    if (uiState.showAddPerksDialog) {
        AddPerksDialog(
            avaliablePerks = uiState.avaliablePerks,
            onDismiss = closeAddPerkDialog,
            onPerkSelected = addPerks
        )
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        Text(
            text = stringResource(R.string.available_to_add_format, uiState.avaliablePerksCount),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(uiState.characterPerks) { perk ->
                PerkItem(
                    perk = perk,
                    deleted = deletePerk
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = addPerk,
            ) {
                Text(text = stringResource(R.string.add))
            }
        }
    }
}

@Composable
fun PerkItem(
    perk: PerkUI,
    modifier: Modifier = Modifier,
    deleted: (Int) -> Unit
) {
    GloomCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextWithImagesByCode(modifier = Modifier.weight(1f), text = perk.text)
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = { deleted(perk.id) }) {
                Icon(
                    Icons.Filled.Delete,
                    stringResource(R.string.delete_perk_desc),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFF666666     )
@Composable
private fun CharacterPerkTabScreenPreview() {
    GloomhavenMasterTheme {
        CharacterPerkTabScreen(
            uiState = CharacterPerksScreenStateUi(
                characterPerks = listOf(
                    PerkUI(1, "Remove two cards #01"),
                    PerkUI(
                        2,
                        "Replace one card #01 with one card #03"
                    ),
                    PerkUI(3, "Add two cards #03"),
                ).toImmutableList(),
                avaliablePerks = persistentListOf(),
                avaliablePerksCount = 4
            ),
            addPerk = {},
            closeAddPerkDialog = {},
            deletePerk = {},
            addPerks = {}
        )
    }
}