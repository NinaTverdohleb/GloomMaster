package com.rumpilstilstkin.gloommaster.benchmark.steps

import com.rumpilstilstkin.gloommaster.testtags.components.CounterTestTags
import com.rumpilstilstkin.gloommaster.testtags.screens.characters.start.CharacterGeneralTabTestTags
import com.rumpilstilstkin.gloommaster.testtags.screens.characters.start.goods.CharacterItemsTabTestTags

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.Direction
import com.rumpilstilstkin.gloommaster.benchmark.clickTag
import com.rumpilstilstkin.gloommaster.benchmark.clickTagRepeated
import com.rumpilstilstkin.gloommaster.benchmark.pressBackAndIdle
import com.rumpilstilstkin.gloommaster.benchmark.swipeLeftOnTag
import com.rumpilstilstkin.gloommaster.benchmark.waitForTag
import com.rumpilstilstkin.gloommaster.testtags.ui.goods.AddGoodsViewTestTags

fun MacrobenchmarkScope.exerciseGeneralTab(count: Int) {
    openTabTop(0)

    //clickTag(CharacterDetailsTestTags.HEADER)
    //pressBackAndIdle()

    // Experience / gold / check-marks counters.
    clickTagRepeated(
        CharacterGeneralTabTestTags.EXPERIENCE_COUNTER,
        CounterTestTags.TEST_TAG_PLUS,
        times = count
    )
    clickTagRepeated(
        CharacterGeneralTabTestTags.EXPERIENCE_COUNTER,
        CounterTestTags.TEST_TAG_MINUS,
        times = count
    )

    clickTagRepeated(
        CharacterGeneralTabTestTags.GOLD_COUNTER,
        CounterTestTags.TEST_TAG_PLUS,
        times = count
    )
    clickTagRepeated(
        CharacterGeneralTabTestTags.GOLD_COUNTER,
        CounterTestTags.TEST_TAG_MINUS,
        times = count
    )

    repeat(count) { clickTag(CharacterGeneralTabTestTags.CHECK_MARK) }

    clickTag(CharacterGeneralTabTestTags.CHOOSE_QUEST)
    back()

    waitForTag(CharacterGeneralTabTestTags.SCROLL_COLUMN).scroll(Direction.DOWN, 1f)
}

fun MacrobenchmarkScope.exerciseStuffTab() {
    openTabTop(1)

    // Add goods → screen → back.
    clickTag(CharacterItemsTabTestTags.ADD_FAB)
    swipeLeftOnTag(AddGoodsViewTestTags.availableGood(0))
    pressBackAndIdle(AddGoodsViewTestTags.availableGood(0))
}
