package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods.delete

import androidx.compose.runtime.Immutable

@Immutable
data class DeleteGoodDialogInput(
    val goodId: Int,
    val characterId: Int,
    val name: String,
)

sealed interface DeleteGoodDialogAction {
    data class DeleteGood(
        val goodId: Int,
        val characterId: Int,
    ) : DeleteGoodDialogAction
}

data object DeleteGoodDialogComplete
