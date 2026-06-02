package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods

sealed interface CharacterItemsTabActions {
    data class DeleteGood(
        val goodId: Int,
    ) : CharacterItemsTabActions

    data class SellGood(
        val goodId: Int,
        val cost: Int,
    ) : CharacterItemsTabActions

    data object AddGood : CharacterItemsTabActions
}
