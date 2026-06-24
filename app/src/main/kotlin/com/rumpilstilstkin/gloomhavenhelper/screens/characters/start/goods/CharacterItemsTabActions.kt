package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods

import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi

sealed interface CharacterItemsTabActions {
    data class DeleteGood(
        val good: GoodUi,
    ) : CharacterItemsTabActions

    data class SellGood(
        val good: GoodUi,
    ) : CharacterItemsTabActions

    data class GoodDetails(
        val good: GoodUi,
    ) : CharacterItemsTabActions

    data object AddGood : CharacterItemsTabActions
}
