package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI

sealed interface CharactersTabState {
    data object Empty : CharactersTabState
    data class Data(
        val filters: Filters,
        val characters: List<CharacterUI>,
    ) : CharactersTabState
}

data class Filters(
    val filterAlive: Boolean = true,
    val filterTeamId: Int? = null,
)