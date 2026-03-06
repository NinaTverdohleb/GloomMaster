package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class CharactersTabStateUi(
    val showAddCharacterDialog: Boolean = false,
    val filterAlive: Boolean = true,
    val canAdd: Boolean = true,
    val characters: ImmutableList<CharacterUI> = persistentListOf()
) {
    companion object {
        fun fixture(
            filterAlive: Boolean = true,
            characters: ImmutableList<CharacterUI> = persistentListOf(
                CharacterUI.fixture(
                    name = "Character 1"
                ),
                CharacterUI.fixture(
                    name = "Character 2"
                )
            )
        ) = CharactersTabStateUi(
            characters = characters,
            filterAlive = filterAlive
        )
    }
}


data class CharactersTabStateLogic(
    val filterAlive: Boolean = true,
    val showAddCharacterDialog: Boolean = false
)

sealed interface CharactersTabAction {
    data class AddCharacter(
        val name: String,
        val level: Int,
        val characterType: CharacterClassType
    ) : CharactersTabAction

    data object SwitchAlive : CharactersTabAction
    data object ShowAddCharacterDialog : CharactersTabAction
    data object CloseAddCharacterDialog : CharactersTabAction
    data class CharacterDetails(val characterId: Int) : CharactersTabAction
}