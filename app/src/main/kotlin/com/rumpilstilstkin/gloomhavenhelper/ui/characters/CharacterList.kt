package com.rumpilstilstkin.gloomhavenhelper.ui.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun CharacterList(
    characters: List<CharacterUI>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        characters.forEach() { character ->
            CharacterItem(
                characterId = character.id,
                imageRes = character.characterClass.imageRes,
                name = character.name,
                level = character.level,
                isAlive = character.isAlive,
                onClick = onClick
            )
        }
    }
}

@Composable
fun CharacterWithDialogList(
    characters: List<CharacterUI>,
    modifier: Modifier = Modifier,
    onSave: (Int, Int) -> Unit,
    onDelete: (Int) -> Unit,
    onLeave: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        characters.forEach() { character ->
            CharacterItemWithDialog(
                characterId = character.id,
                imageRes = character.characterClass.imageRes,
                name = character.name,
                level = character.level,
                isAlive = character.isAlive,
                onSave = onSave,
                onDelete = onDelete,
                onLeave = onLeave
            )
        }
    }
}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        CharacterWithDialogList(
            characters = listOf(
                CharacterUI(
                    name = "Name",
                    level = 6,
                    characterClass = CharacterClassUI(
                        imageRes = R.drawable.br,
                        name = "Name",
                        classType = CharacterClassType.Brute
                    ), teamName = null
                ),
                CharacterUI(
                    name = "Name2",
                    level = 6,
                    characterClass = CharacterClassUI(
                        imageRes = R.drawable.br,
                        name = "Name",
                        classType = CharacterClassType.Brute
                    ), teamName = null
                )
            ),
            onSave = { _, _ -> },
            onDelete = { },
            onLeave = { }
        )
    }

}