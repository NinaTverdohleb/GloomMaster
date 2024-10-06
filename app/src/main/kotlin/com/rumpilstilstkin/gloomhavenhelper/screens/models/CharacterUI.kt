package com.rumpilstilstkin.gloomhavenhelper.screens.models

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo


data class CharacterUI(
    val name: String,
    val level: Int,
    val characterClass: CharacterClassUI,
    val id: Int = 0,
    val teamName: String?,
    val isAlive: Boolean = true
)

fun CharacterInfo.toUi() = CharacterUI(
    name = this.name,
    level = this.level,
    id = this.id,
    characterClass = this.characterClass.toUi(),
    isAlive = this.isAlive,
    teamName = this.team?.name

)