package com.rumpilstilstkin.gloomhavenhelper.ui.goods

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GloomhavenIcons
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Foot
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class AddGoodsViewState(
    val selectedGoods: ImmutableList<GoodUi> = persistentListOf(),
    val availableGoods: ImmutableList<GoodUi> = persistentListOf(),
    val selectedFilter: GoodType? = null,
    val searchText: String = "",
) {
    companion object {
        fun fixture() = AddGoodsViewState(
            searchText = "",
            availableGoods = persistentListOf(
                GoodUi(
                    number = 1,
                    name = "Boots of Striding",
                    typeImage = GloomhavenIcons.GoodTypes.Foot,
                    cost = 20,
                    image = ""
                ),
                GoodUi(
                    number = 2,
                    name = "Boots of Striding",
                    typeImage = GloomhavenIcons.GoodTypes.Foot,
                    cost = 20,
                    image = ""
                )
            ),
            selectedGoods = persistentListOf(
                GoodUi(
                    number = 3,
                    name = "Boots of Striding",
                    typeImage = GloomhavenIcons.GoodTypes.Foot,
                    cost = 20,
                    image = ""
                ),
                GoodUi(
                    number = 1,
                    name = "Boots of Striding",
                    typeImage = GloomhavenIcons.GoodTypes.Foot,
                    cost = 20,
                    image = ""
                )
            ),
            selectedFilter = GoodType.Arm,
        )
    }
}