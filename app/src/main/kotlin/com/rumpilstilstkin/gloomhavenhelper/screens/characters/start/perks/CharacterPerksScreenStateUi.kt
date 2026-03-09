package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PerkUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class CharacterPerksScreenStateUi (
    val characterPerks: ImmutableList<PerkUI> = persistentListOf(),
    val avaliablePerks: ImmutableList<PerkUI> = persistentListOf(),
    val avaliablePerksCount: Int = 0,
    val showAddPerksDialog: Boolean = false,
)

data class CharacterPerksScreenStateLogic(
    val showAddPerksDialog: Boolean = false,
)