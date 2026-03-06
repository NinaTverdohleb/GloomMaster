package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.components.EmptyCharacters
import com.rumpilstilstkin.gloomhavenhelper.ui.characters.CharacterItem
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CharactersTabScreen(
    uiState: CharactersTabStateUi,
    addCharacter: () -> Unit,
    openCharacterDetails: (Int) -> Unit,
    switchAlive: (Boolean) -> Unit,
) = Column(
    modifier = Modifier.fillMaxSize()
) {
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
            checked = uiState.filterAlive,
            onCheckedChange = switchAlive,
        )
    }

    if (uiState.characters.isEmpty()) {
        EmptyCharacters(
            modifier = Modifier.weight(1f),
        )
    } else {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(uiState.characters) { character ->
                CharacterItem(
                    character = character,
                    onItemClick = openCharacterDetails
                )
            }
        }
    }

    if (uiState.canAdd) {
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
    GloomhavenHalperTheme {
        CharactersTabScreen(
            uiState = CharactersTabStateUi.fixture(),
            switchAlive = {},
            addCharacter = {},
            openCharacterDetails = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun CharactersTabScreenEmptyPreview() {
    GloomhavenHalperTheme {
        CharactersTabScreen(
            uiState = CharactersTabStateUi.fixture(characters = persistentListOf()),
            switchAlive = {},
            addCharacter = {},
            openCharacterDetails = {}
        )
    }
}