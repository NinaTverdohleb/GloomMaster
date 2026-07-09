package com.rumpilstilstkin.gloommaster.screens.characters.dialogs.add

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloommaster.screens.models.CharacterClassTypeUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class AddCharacterDialogState(
    val name: String = "",
    val level: Int = 1,
    val avaliableClasses: ImmutableList<CharacterClassTypeUI> = persistentListOf(CharacterClassTypeUI.Brute),
    val selectedClass: CharacterClassTypeUI = avaliableClasses.firstOrNull() ?: CharacterClassTypeUI.Brute,
) {
    companion object {
        fun fixture(
            name: String = "",
            level: Int = 1,
            avaliableClasses: ImmutableList<CharacterClassTypeUI> =
                persistentListOf(
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

    data class UpdateName(
        val name: String,
    ) : AddCharacterDialogAction

    data class UpdateLevel(
        val level: Int,
    ) : AddCharacterDialogAction

    data class SelectType(
        val type: CharacterClassTypeUI,
    ) : AddCharacterDialogAction
}

data object AddCharacterDialogComplete
