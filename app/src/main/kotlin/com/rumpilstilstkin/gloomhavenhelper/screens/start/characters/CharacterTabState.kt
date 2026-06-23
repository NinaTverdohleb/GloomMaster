package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class CharactersTabStateUi(
    val filterAlive: Boolean = true,
    val canAdd: Boolean = true,
    val characters: ImmutableList<CharacterUI> = persistentListOf(),
    val avaliableClasses: ImmutableList<CharacterClassTypeUI> = persistentListOf(),
) {
    companion object {
        fun fixture(
            filterAlive: Boolean = true,
            characters: ImmutableList<CharacterUI> =
                persistentListOf(
                    CharacterUI.fixture(
                        id = 1,
                        name = "Character 1",
                    ),
                    CharacterUI.fixture(
                        id = 2,
                        name = "Character 2",
                    ),
                ),
            avaliableClasses: ImmutableList<CharacterClassTypeUI> =
                persistentListOf(
                    CharacterClassTypeUI.Brute,
                    CharacterClassTypeUI.Elementalist,
                ),
        ) = CharactersTabStateUi(
            characters = characters,
            filterAlive = filterAlive,
            avaliableClasses = avaliableClasses,
        )
    }
}

data class CharactersTabStateLogic(
    val filterAlive: Boolean = true,
)

sealed interface CharactersTabAction {

    data object SwitchAlive : CharactersTabAction

    data object ShowAddCharacterDialog : CharactersTabAction

    data class SwitchClassAvailability(
        val type: CharacterClassTypeUI,
    ) : CharactersTabAction

    data class CharacterMenu(
        val character: CharacterUI,
    ) : CharactersTabAction
}
