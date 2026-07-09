package com.rumpilstilstkin.gloommaster.screens.characters.start.perks

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloommaster.screens.models.PerkUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class CharacterPerksScreenStateUi(
    val characterPerks: ImmutableList<PerkUI> = persistentListOf(),
    val avaliablePerks: ImmutableList<PerkUI> = persistentListOf(),
    val avaliablePerksCount: Int = 0,
)
