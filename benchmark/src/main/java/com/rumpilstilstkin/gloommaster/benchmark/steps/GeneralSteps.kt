package com.rumpilstilstkin.gloommaster.benchmark.steps

import com.rumpilstilstkin.gloommaster.testtags.components.ToolbarTestTags

import androidx.benchmark.macro.MacrobenchmarkScope
import com.rumpilstilstkin.gloommaster.benchmark.clickTag
import com.rumpilstilstkin.gloommaster.testtags.components.NavigationBarTestTags

fun MacrobenchmarkScope.back() {
    clickTag(ToolbarTestTags.BACK)
    device.waitForIdle()
}

fun MacrobenchmarkScope.openTabBottom(tab: Int) {
    clickTag(NavigationBarTestTags.tabBottom(tab))
    device.waitForIdle()
}

fun MacrobenchmarkScope.openTabTop(tab: Int) {
    clickTag(NavigationBarTestTags.tabTop(tab))
    device.waitForIdle()
}