package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods.sell

import androidx.compose.runtime.Immutable

@Immutable
data class SellGoodDialogInput(
    val goodId: Int,
    val characterId: Int,
    val name: String,
    val cost: Int,
)

sealed interface SellGoodDialogAction {
    data class SellGood(
        val goodId: Int,
        val characterId: Int,
        val cost: Int,
    ) : SellGoodDialogAction
}

data object SellGoodDialogComplete
