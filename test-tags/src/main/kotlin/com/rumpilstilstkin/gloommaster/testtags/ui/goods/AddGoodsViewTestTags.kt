package com.rumpilstilstkin.gloommaster.testtags.ui.goods

object AddGoodsViewTestTags {
    private const val AVAILABLE_GOOD_PREFIX = "AddGoodsViewAvailableGood"
    const val ADD_GOOD_ACTION = "AddGoodsViewAddGoodAction"

    fun availableGood(index: Int) = "$AVAILABLE_GOOD_PREFIX$index"
}
