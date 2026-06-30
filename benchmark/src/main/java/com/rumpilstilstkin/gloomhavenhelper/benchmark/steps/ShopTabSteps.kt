package com.rumpilstilstkin.gloomhavenhelper.benchmark.steps

import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.start.shop.ShopTabTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.ui.goods.AddGoodsViewTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.ui.goods.GoodFiltersTestTags

import androidx.benchmark.macro.MacrobenchmarkScope
import com.rumpilstilstkin.gloomhavenhelper.benchmark.clickTag
import com.rumpilstilstkin.gloomhavenhelper.benchmark.pressBackAndIdle
import com.rumpilstilstkin.gloomhavenhelper.benchmark.swipeLeftOnTag
import com.rumpilstilstkin.gloomhavenhelper.benchmark.waitForTag

fun MacrobenchmarkScope.exerciseShopTab() {
    (0..5).forEach { index ->
        clickTag(GoodFiltersTestTags.filter(index))
    }

    // Type into search.
    waitForTag(GoodFiltersTestTags.SEARCH_FIELD).apply {
        click()
        text = "42"
        text = ""
    }
    device.waitForIdle()

    clickTag(ShopTabTestTags.ADD_FAB)
    swipeLeftOnTag(AddGoodsViewTestTags.availableGood(0))
    pressBackAndIdle(AddGoodsViewTestTags.availableGood(0))
}