package com.rumpilstilstkin.gloommaster.screens.characters.dialogs.character

data class CharacterEditNameDialogUiState(
    val name: String = "",
)

sealed interface CharacterEditNameDialogAction {
    data class Save(
        val name: String,
    ) : CharacterEditNameDialogAction
}

data object CharacterEditNameDialogComplete
