package com.rumpilstilstkin.gloommaster.screens.characters.start

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloommaster.screens.models.CharacterUI

sealed interface CharacterDetailsAction {
    data object Back : CharacterDetailsAction

    data object OpenNameDialog : CharacterDetailsAction
}

@Immutable
sealed interface CharacterDetailsStateUi {
    data class Data(
        val character: CharacterUI,
    ) : CharacterDetailsStateUi

    data object Loading : CharacterDetailsStateUi
}
