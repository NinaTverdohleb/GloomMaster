package com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.delete

sealed interface DeleteCharacterDialogAction {
    data class DeleteCharacter(val characterId: Int) : DeleteCharacterDialogAction
}

data object DeleteCharacterDialogComplete
