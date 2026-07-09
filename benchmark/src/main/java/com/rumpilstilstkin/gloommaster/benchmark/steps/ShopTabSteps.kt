package com.rumpilstilstkin.gloommaster.benchmark.steps

import com.rumpilstilstkin.gloommaster.testtags.screens.start.shop.ShopTabTestTags
import com.rumpilstilstkin.gloommaster.testtags.ui.goods.AddGoodsViewTestTags
import com.rumpilstilstkin.gloommaster.testtags.ui.goods.GoodFiltersTestTags

import androidx.benchmark.macro.MacrobenchmarkScope
import com.rumpilstilstkin.gloommaster.benchmark.clickTag
import com.rumpilstilstkin.gloommaster.benchmark.pressBackAndIdle
import com.rumpilstilstkin.gloommaster.benchmark.swipeLeftOnTag
import com.rumpilstilstkin.gloommaster.benchmark.waitForTag

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