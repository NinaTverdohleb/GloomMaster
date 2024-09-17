package com.rumpilstilstkin.gloomhavenhelper.screens.models

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo


data class CharacterUI(
    val name: String,
    val level: Int,
    val characterClass: CharacterClassUI,
    val id: Int = 0,
    val isAlive: Boolean = true,
)

fun CharacterInfo.toUI() = CharacterUI(
    name = this.name,
    level = this.level,
    id = this.id,
    characterClass = this.characterClass.toUI(),
    isAlive = this.isAlive
)