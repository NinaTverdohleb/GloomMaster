package com.rumpilstilstkin.gloommaster.screens.characters.start.general.text

sealed interface EditTextDialogAction {
    data class UpdateText(
        val text: String,
    ) : EditTextDialogAction
}
