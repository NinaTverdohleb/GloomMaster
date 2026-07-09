package com.rumpilstilstkin.gloommaster.screens.characters.start.perks.add

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloommaster.screens.models.PerkUI
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class AddPerksDialogInput(
    val availablePerks: ImmutableList<PerkUI>,
    val characterId: Int,
)

sealed interface AddPerksDialogAction {
    data class AddPerks(
        val perksIds: List<Int>,
        val characterId: Int,
    ) : AddPerksDialogAction
}

data object AddPerksDialogComplete
