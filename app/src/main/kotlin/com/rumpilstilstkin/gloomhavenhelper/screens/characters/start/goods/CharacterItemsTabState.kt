package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods

sealed interface CharacterItemsTabActions {
    data class DeleteGood(val characterGoodId: Int) : CharacterItemsTabActions
    data class SellGood(val characterGoodId: Int) : CharacterItemsTabActions
    data object AddGood : CharacterItemsTabActions
}