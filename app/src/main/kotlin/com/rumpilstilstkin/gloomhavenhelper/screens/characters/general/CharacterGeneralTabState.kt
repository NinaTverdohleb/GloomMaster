package com.rumpilstilstkin.gloomhavenhelper.screens.characters.general

import com.rumpilstilstkin.gloomhavenhelper.ui.TaskUI

data class CharacterGeneralTabState(
    val name: String,
    val level: Int,
    val experience: Int,
    val gold: Int,
    val hasTeam: Boolean,
    val nextLevel: Int,
    val notes: String,
    val mainTask: TaskUI?,
    val checkMarks: Int = 0
) {
    companion object {
        val Empty = CharacterGeneralTabState(
            name = "",
            level = 0,
            experience = 0,
            gold = 0,
            hasTeam = false,
            nextLevel = 0,
            notes = "",
            mainTask = null,
            checkMarks = 0
        )
    }
}
