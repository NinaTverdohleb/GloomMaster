package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods

sealed interface CharacterItemsTabActions {
    data class DeleteGood(val goodNumber: Int) : CharacterItemsTabActions
    data class SellGood(val goodNumber: Int, val cost: Int) : CharacterItemsTabActions
    data object AddGood : CharacterItemsTabActions
}