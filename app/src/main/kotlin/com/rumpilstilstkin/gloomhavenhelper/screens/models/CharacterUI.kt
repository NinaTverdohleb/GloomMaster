package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI.Companion.toCharacterClassTypeUI

@Immutable
data class CharacterUI(
    val name: String,
    val level: Int,
    val characterClass: CharacterClassTypeUI,
    val id: Int = 0,
    val teamName: String?,
    val isAlive: Boolean = true,
) {
    companion object {
        fun fixture(
            id: Int = 1,
            name: String = "Name2",
            level: Int = 6,
            characterClass: CharacterClassTypeUI = CharacterClassTypeUI.Brute,
            teamName: String? = null,
            isAlive: Boolean = true,
        ) = CharacterUI(
            id = id,
            name = name,
            level = level,
            characterClass = characterClass,
            teamName = teamName,
            isAlive = isAlive
        )
    }
}

fun CharacterInfo.toUi() =
    CharacterUI(
        name = this.name,
        level = this.level,
        id = this.id,
        characterClass = this.characterType.toCharacterClassTypeUI(),
        isAlive = this.isAlive,
        teamName = this.team?.name,
    )
