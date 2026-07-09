package com.rumpilstilstkin.gloommaster.screens.characters.dialogs.delete

sealed interface DeleteCharacterDialogAction {
    data class DeleteCharacter(
        val characterId: Int,
    ) : DeleteCharacterDialogAction
}

data object DeleteCharacterDialogComplete
