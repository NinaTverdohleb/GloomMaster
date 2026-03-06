package com.rumpilstilstkin.gloomhavenhelper.ui.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toImage
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.toImage
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

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
                character = character,
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
                CharacterUI.fixture("name 1"),
                CharacterUI.fixture("name 2")
            ),
            onSave = { _, _ -> },
            onDelete = { },
            onLeave = { }
        )
    }

}