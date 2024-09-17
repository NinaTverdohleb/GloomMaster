package com.rumpilstilstkin.gloomhavenhelper.screens.teem.create

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI

data class TeamCreateUiState(
    val name: String,
    val classes: List<CharacterClassUI> = emptyList(),
    val characters: List<CharacterUI> = emptyList(),
    val done: Boolean = false,
    val showCharacterDialog: Boolean = false,
    val canAdd: Boolean = true,
) {
    companion object {
        val Empty = TeamCreateUiState("Друзьяшки")
    }
}

fun TeamCreateUiState.toTeamForSave(): TeamInfoForSave =
    TeamInfoForSave(
        name = name,
        characters = characters.map {
            it.toCharacterForSave()
        }
    )

fun CharacterUI.toCharacterForSave() =  CharacterForSave(
    name = name,
    level = level,
    classId = characterClass.id
)