package com.rumpilstilstkin.gloomhavenhelper.screens.characters.perks

import com.rumpilstilstkin.gloomhavenhelper.screens.models.PerkUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CharacterPerksScreenState (
    val characterPerks: ImmutableList<PerkUI> = persistentListOf(),
    val avaliablePerks: ImmutableList<PerkUI> = persistentListOf(),
    val avaliablePerksCount: Int = 0
)