package com.rumpilstilstkin.gloommaster.screens.teem.goods

import com.rumpilstilstkin.gloommaster.domain.entity.GoodType
import com.rumpilstilstkin.gloommaster.screens.models.GoodUi
import com.rumpilstilstkin.gloommaster.ui.goods.AddGoodsViewState

data class AddGoodsForTeamUiState(
    val goodsState: AddGoodsViewState = AddGoodsViewState(),
)

data class AddGoodsForTeamLogicState(
    val selectedGoods: List<GoodUi> = emptyList(),
    val selectedFilter: GoodType? = null,
    val searchText: String = "",
)

sealed interface AddGoodsForTeamAction {
    data class SelectGood(
        val good: GoodUi,
    ) : AddGoodsForTeamAction

    data class UnselectGood(
        val good: GoodUi,
    ) : AddGoodsForTeamAction

    data object AddSelectedGoods : AddGoodsForTeamAction

    data class SelectFilter(
        val type: GoodType,
    ) : AddGoodsForTeamAction

    data class SearchTextChange(
        val text: String,
    ) : AddGoodsForTeamAction

    data class OpenGood(
        val good: GoodUi,
    ) : AddGoodsForTeamAction

    data object Back : AddGoodsForTeamAction
}
