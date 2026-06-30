package com.rumpilstilstkin.gloomhavenhelper.benchmark.steps

import androidx.benchmark.macro.MacrobenchmarkScope
import com.rumpilstilstkin.gloomhavenhelper.benchmark.clickTag
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.start.scenarios.ScenariosTabTestTags

fun MacrobenchmarkScope.exerciseScenariosTab(repeat: Int) {
       repeat(repeat) {
        clickTag(ScenariosTabTestTags.ADD_FAB)
        back()
    }
}