package com.rumpilstilstkin.gloomhavenhelper.screens.start.shop

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.AddGoodsScreenActions
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ShopTabStateUi(
    val avaliableGoods: ImmutableList<GoodUi> = persistentListOf(),
    val selectedFilter: GoodType? = null,
    val searchText: String = "",
    val canAdd: Boolean = true,
)

data class ShopTabStateLogic(
    val selectedFilter: GoodType? = null,
    val searchText: String = "",
    val canAdd: Boolean = true,
)

sealed interface ShopTabAction {
    data object AddGood : ShopTabAction
    data class RemoveGood(val number: Int) : ShopTabAction
    data class SelectFilter(val type: GoodType) : ShopTabAction
    data class SearchTextChange(val text: String) : ShopTabAction
}
