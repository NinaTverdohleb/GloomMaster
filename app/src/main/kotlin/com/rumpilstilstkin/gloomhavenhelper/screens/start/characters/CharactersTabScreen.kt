package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomSwitch
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomFab
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.components.CharacterAvailableClasses
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.components.EmptyCharacters
import com.rumpilstilstkin.gloomhavenhelper.ui.characters.CharacterItemFilled
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CharactersTabScreen(
    state: CharactersTabStateUi,
    addCharacter: () -> Unit,
    openCharacterMenu: (CharacterUI) -> Unit,
    switchAlive: (Boolean) -> Unit,
    toggleClass: (CharacterClassTypeUI) -> Unit,
) = Scaffold(
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
        if (state.canAdd) {
            GloomFab(
                icon = AppIcon.Plus,
                onClick = addCharacter,
            )
        }
    }
) { innerPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        CharacterAvailableClasses(
            availableClasses = state.avaliableClasses,
            onToggle = toggleClass,
        )
        Spacer(
            modifier = Modifier.height(24.dp),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GloomSwitch(
                selected = state.filterAlive,
                onCheckedChange = switchAlive,
            )

            Spacer(
                modifier = Modifier.width(8.dp),
            )
            Text(
                text = stringResource(R.string.show_only_active),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        if (state.characters.isEmpty()) {
            EmptyCharacters(
                modifier = Modifier.weight(1f),
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
            ) {
                items(
                    items = state.characters,
                    key = { character -> character.id }
                ) { character ->
                    CharacterItemFilled(
                        character = character,
                        onClick = { openCharacterMenu(character) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun CharactersTabScreenPreview() {
    GloomhavenMasterTheme {
        CharactersTabScreen(
            state = CharactersTabStateUi.fixture(),
            switchAlive = {},
            addCharacter = {},
            openCharacterMenu = {},
            toggleClass = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun CharactersTabScreenEmptyPreview() {
    GloomhavenMasterTheme {
        CharactersTabScreen(
            state = CharactersTabStateUi.fixture(characters = persistentListOf()),
            switchAlive = {},
            addCharacter = {},
            openCharacterMenu = {},
            toggleClass = {},
        )
    }
}
