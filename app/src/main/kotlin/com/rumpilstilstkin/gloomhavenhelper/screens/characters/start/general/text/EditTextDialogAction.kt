package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.text

sealed interface EditTextDialogAction {
    data class UpdateText(
        val text: String,
    ) : EditTextDialogAction
}
