package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.AddGoodsViewState

data class AddGoodsForCharacterScreenUiState(
    val goodsState: AddGoodsViewState = AddGoodsViewState(),
    val allGold: Int = 0,
    val goodsGold: Int = 0,
)

data class AddGoodsForCharacterScreenLogicState(
    val selectedGoods: List<GoodUi> = emptyList(),
    val selectedFilter: GoodType? = null,
    val searchText: String = "",
    val discount: Int = 0,
    val allGold: Int = 0,
)

sealed interface AddGoodsForCharacterScreenActions {
    data class SelectGood(
        val good: GoodUi,
    ) : AddGoodsForCharacterScreenActions

    data class UnselectGood(
        val good: GoodUi,
    ) : AddGoodsForCharacterScreenActions

    data object AddSelectedGoods : AddGoodsForCharacterScreenActions

    data object BuySelectedGoods : AddGoodsForCharacterScreenActions

    data object Close : AddGoodsForCharacterScreenActions

    data class SelectFilter(
        val type: GoodType,
    ) : AddGoodsForCharacterScreenActions

    data class SearchTextChange(
        val text: String,
    ) : AddGoodsForCharacterScreenActions
}
