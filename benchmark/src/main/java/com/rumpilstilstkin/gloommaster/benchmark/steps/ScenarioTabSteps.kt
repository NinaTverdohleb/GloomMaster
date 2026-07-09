package com.rumpilstilstkin.gloommaster.benchmark.steps

import androidx.benchmark.macro.MacrobenchmarkScope
import com.rumpilstilstkin.gloommaster.benchmark.clickTag
import com.rumpilstilstkin.gloommaster.testtags.screens.start.scenarios.ScenariosTabTestTags

fun MacrobenchmarkScope.exerciseScenariosTab(repeat: Int) {
       repeat(repeat) {
        clickTag(ScenariosTabTestTags.ADD_FAB)
        back()
    }
}