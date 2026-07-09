package com.rumpilstilstkin.gloommaster.screens.characters.start.perks.delete

import androidx.compose.runtime.Immutable

@Immutable
data class DeletePerkDialogInput(
    val perkId: Int,
    val characterId: Int,
)

sealed interface DeletePerkDialogAction {
    data class DeletePerk(
        val perkId: Int,
        val characterId: Int,
    ) : DeletePerkDialogAction
}

data object DeletePerkDialogComplete
