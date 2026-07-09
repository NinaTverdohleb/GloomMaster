package com.rumpilstilstkin.gloommaster.screens.characters.dialogs.menu

import com.rumpilstilstkin.gloommaster.screens.models.CharacterUI

sealed interface MenuCharacterDialogAction {
    data class UpdateLevel(
        val character: CharacterUI,
        val level: Int,
    ) : MenuCharacterDialogAction

    data class LeaveCharacter(
        val character: CharacterUI,
    ) : MenuCharacterDialogAction

    data class MakeCharacterAlive(
        val character: CharacterUI,
    ) : MenuCharacterDialogAction

    data class OpenCharacterDetails(
        val character: CharacterUI,
    ) : MenuCharacterDialogAction

    data class DeleteCharacter(
        val character: CharacterUI,
    ) : MenuCharacterDialogAction
}

data class MenuCharacterDialogComplete(
    val result: MenuCharacterResult,
)

sealed interface MenuCharacterResult {
    data class CharacterLeft(
        val character: CharacterUI,
    ) : MenuCharacterResult

    data class CharacterMadeAlive(
        val character: CharacterUI,
    ) : MenuCharacterResult

    data class OpenCharacterDetails(
        val character: CharacterUI,
    ) : MenuCharacterResult

    data class DeleteCharacterRequest(
        val character: CharacterUI,
    ) : MenuCharacterResult
}
