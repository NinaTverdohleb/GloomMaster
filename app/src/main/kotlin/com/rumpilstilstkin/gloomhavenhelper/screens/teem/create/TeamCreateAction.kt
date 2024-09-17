package com.rumpilstilstkin.gloomhavenhelper.screens.teem.create

sealed interface TeamCreateAction {
    data class EditName(val name: String) : TeamCreateAction
    data object ShowCharacterDialog : TeamCreateAction
    data object HideCharacterDialog : TeamCreateAction
    data class AddCharacter(val name: String, val level: Int, val classId: Int) : TeamCreateAction
    data object Save : TeamCreateAction
    data class DeleteCharacter(val id: Int) : TeamCreateAction
    data class LeaveCharacter(val id: Int) : TeamCreateAction
    data class UpdateCharacter(val id: Int, val level: Int) : TeamCreateAction
}