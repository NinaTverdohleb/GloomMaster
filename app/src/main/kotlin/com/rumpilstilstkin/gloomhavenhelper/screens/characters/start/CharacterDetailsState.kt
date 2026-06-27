package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI

sealed interface CharacterDetailsAction {
    data object Back : CharacterDetailsAction

    data object OpenNameDialog : CharacterDetailsAction
}

@Immutable
data class CharacterDetailsStateUi(
    val character: CharacterUI = CharacterUI.fixture(),
    val teamName: String = "",
    val isActive: Boolean = false,
)
