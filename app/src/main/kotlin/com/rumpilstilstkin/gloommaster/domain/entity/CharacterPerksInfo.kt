package com.rumpilstilstkin.gloommaster.domain.entity

data class CharacterPerksInfo(
    val characterPerks: List<Perk>,
    val avaliablePerks: List<Perk>,
    val avaliablePerksCount: Int,
)
