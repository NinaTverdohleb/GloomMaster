package com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.add

import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class AddCharacterDialogState(
    val name: String = "",
    val level: Int = 0,
    val avaliableClasses: ImmutableList<CharacterClassTypeUI>,
    val selectedClass: CharacterClassTypeUI = avaliableClasses.first(),
) {
    companion object {
        fun fixture(
            name: String = "",
            level: Int = 0,
            avaliableClasses: ImmutableList<CharacterClassTypeUI> = persistentListOf(
                CharacterClassTypeUI.Brute,
                CharacterClassTypeUI.Spellweaver,
            ),
            selectedClass: CharacterClassTypeUI = avaliableClasses.first(),
        ) = AddCharacterDialogState(
            name = name,
            level = level,
            avaliableClasses = avaliableClasses,
            selectedClass = selectedClass,
        )
    }
}

sealed interface AddCharacterDialogAction {
    data object AddCharacter : AddCharacterDialogAction
    data class UpdateName(val name: String) : AddCharacterDialogAction
    data class UpdateLevel(val level: Int) : AddCharacterDialogAction
    data class SelectType(val type: CharacterClassTypeUI) : AddCharacterDialogAction
}

data object AddCharacterDialogComplete