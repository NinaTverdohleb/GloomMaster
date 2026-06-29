package com.rumpilstilstkin.gloomhavenhelper.benchmark.steps

import androidx.benchmark.macro.MacrobenchmarkScope
import com.rumpilstilstkin.gloomhavenhelper.benchmark.AppTags
import com.rumpilstilstkin.gloomhavenhelper.benchmark.TestConsts
import com.rumpilstilstkin.gloomhavenhelper.benchmark.clickTag

fun MacrobenchmarkScope.openAndPlayScenario() {
    clickTag(AppTags.ScenarioBlock.scenarioCard(TestConsts.SCENARIO_NUMBER))
    clickTag(AppTags.MenuScenarioDialog.PLAY_SCENARIO_BUTTON)
}
