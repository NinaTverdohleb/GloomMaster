package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

sealed interface CharactersTabAction {
    data class AddCharacter(val name: String, val level: Int, val classId: Int) : CharactersTabAction
    data object SwitchAlive: CharactersTabAction
    data class ShowByTeam(val teamId: Int?): CharactersTabAction
}