package com.rumpilstilstkin.gloomhavenhelper.domain.entity

data class CharacterPerksInfo(
    val characterPerks: List<Perk>,
    val avaliablePerks: List<Perk>,
    val avaliablePerksCount: Int,
)
