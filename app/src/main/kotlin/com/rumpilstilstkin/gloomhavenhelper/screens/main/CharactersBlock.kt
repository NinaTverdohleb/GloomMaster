package com.rumpilstilstkin.gloomhavenhelper.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.ui.characters.CharacterList

@Composable
fun CharactersBlock(
    characters: List<CharacterUI>,
    canAdd: Boolean,
    modifier: Modifier = Modifier,
    onAction: (MainScreenAction) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Игроки",
            style = MaterialTheme.typography.titleMedium,
        )
        CharacterList(
            characters = characters,
            onSave = { characterId, newLevel ->
                onAction.invoke(
                    MainScreenAction.EditCharacter(
                        characterId,
                        newLevel
                    )
                )
            },
            onDelete = { onAction.invoke(MainScreenAction.DeleteCharacter(it)) },
            onLeave = { onAction.invoke(MainScreenAction.LeaveCharacter(it)) }
        )
        if (canAdd) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onAction.invoke(MainScreenAction.ShowAddCharacterDialog)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Добавить персонажа")
            }
        }
    }
}