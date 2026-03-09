package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI

sealed interface CharacterDetailsAction {
    data object Back : CharacterDetailsAction
    data object ShowDeleteDialog : CharacterDetailsAction
    data object HideDeleteDialog : CharacterDetailsAction
    data object ConfirmDelete : CharacterDetailsAction
    data object ShowNameDialog : CharacterDetailsAction
    data object HideNameDialog : CharacterDetailsAction
    data class SaveName(val name: String) : CharacterDetailsAction
    data class ChangeTeam(val teamId: Int) : CharacterDetailsAction
    data object ShowChangeLevelDialog : CharacterDetailsAction
    data object HideChangeLevelDialog : CharacterDetailsAction
    data class ChangeLevel(val level: Int) : CharacterDetailsAction
    data object Retire: CharacterDetailsAction
}

data class CharacterDetailsStateLogic(
    val showDeleteDialog: Boolean = false,
    val showNameDialog: Boolean = false,
    val showChangeLevelDialog: Boolean = false,
)


@Immutable
data class CharacterDetailsStateUi(
    val level: Int = 0,
    val name: String = "",
    val type: CharacterClassTypeUI = CharacterClassTypeUI.Brute,
    val teamName: String = "",
    val showDeleteDialog: Boolean = false,
    val showNameDialog: Boolean = false,
    val showChangeLevelDialog: Boolean = false,
    val isActive: Boolean = false,
)