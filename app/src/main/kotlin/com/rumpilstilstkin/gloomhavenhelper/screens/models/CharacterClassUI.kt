package com.rumpilstilstkin.gloomhavenhelper.screens.models

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClass

data class CharacterClassUI(
    val name: String,
    val id: Int,
    val imageRes: Int,
)

fun CharacterClass.toUI() = CharacterClassUI(
    name = this.name,
    id = this.id,
    imageRes = this.image,
)