package com.rumpilstilstkin.gloomhavenhelper.benchmark.steps

import androidx.benchmark.macro.MacrobenchmarkScope
import com.rumpilstilstkin.gloomhavenhelper.benchmark.AppTags
import com.rumpilstilstkin.gloomhavenhelper.benchmark.clickTag

fun MacrobenchmarkScope.back() {
    clickTag(AppTags.BACK)
}