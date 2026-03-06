package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo

@Immutable
data class CharacterUI(
    val name: String,
    val level: Int,
    val characterClass: CharacterClassUI,
    val id: Int = 0,
    val teamName: String?,
    val isAlive: Boolean = true
) {
    companion object {
        fun fixture(
            name: String = "Name2",
            level: Int = 6,
            characterClass: CharacterClassUI = CharacterClassUI(
                name = "Name",
                classType = CharacterClassType.Brute
            ),
            teamName: String? = null
        ) = CharacterUI(
            name = name,
            level = level,
            characterClass = characterClass,
            teamName = teamName
        )
    }
}

fun CharacterInfo.toUi() = CharacterUI(
    name = this.name,
    level = this.level,
    id = this.id,
    characterClass = this.characterClass.toUi(),
    isAlive = this.isAlive,
    teamName = this.team?.name

)