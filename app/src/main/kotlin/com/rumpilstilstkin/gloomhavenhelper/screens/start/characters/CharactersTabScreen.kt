package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character.CharacterEditLevelDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.components.CharacterAvailableClasses
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.components.EmptyCharacters
import com.rumpilstilstkin.gloomhavenhelper.ui.characters.CharacterItem
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CharactersTabScreen(
    state: CharactersTabStateUi,
    addCharacter: () -> Unit,
    openCharacterDetails: (Int) -> Unit,
    switchAlive: (Boolean) -> Unit,
    toggleClass: (CharacterClassTypeUI) -> Unit,
    changeLevel: (Int, Int) -> Unit
) = Column(
    modifier = Modifier.fillMaxSize()
) {
    var selectedCharacter by remember { mutableStateOf<CharacterUI?>(null) }

    selectedCharacter?.let { character ->
        CharacterEditLevelDialog(
            characterLevel = character.level,
            characterName = character.name,
            characterClass = character.characterClass,
            dismiss = { selectedCharacter = null },
            changeLevel = {
                changeLevel(character.id, it)
                selectedCharacter = null
            }
        )
    }

    CharacterAvailableClasses(
        availableClasses = state.avaliableClasses,
        onToggle = toggleClass
    )
    Spacer(
        modifier = Modifier.height(8.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Показывать только активных",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.End
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Switch(
            checked = state.filterAlive,
            onCheckedChange = switchAlive,
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
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(state.characters) { character ->
                CharacterItem(
                    character = character,
                    onItemClick = openCharacterDetails,
                    onLevelClick = { selectedCharacter = character }
                )
            }
        }
    }

    if (state.canAdd) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = addCharacter,
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(
                text = "Добавить персонажа",
                fontSize = 16.sp
            )
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
            openCharacterDetails = {},
            toggleClass = {},
            changeLevel = {_,_ ->}
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
            openCharacterDetails = {},
            toggleClass = {},
            changeLevel = {_,_ ->}
        )
    }
}