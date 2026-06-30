package com.rumpilstilstkin.gloomhavenhelper.benchmark.steps

import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.scenario.dialog.menu.MenuScenarioDialogTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.start.team.TeamTabScreenTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.start.team.component.ScenarioBlockTestTags

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.Direction
import com.rumpilstilstkin.gloomhavenhelper.benchmark.clickTag
import com.rumpilstilstkin.gloomhavenhelper.benchmark.existTag
import com.rumpilstilstkin.gloomhavenhelper.benchmark.waitForTag

fun MacrobenchmarkScope.openAndPlayScenario() {
    if (!existTag(ScenarioBlockTestTags.scenarioCard(0))) {
        waitForTag(TeamTabScreenTestTags.ROOT_COLUMN).scroll(Direction.DOWN, 0.8f)
    }
    clickTag(ScenarioBlockTestTags.scenarioCard(0))
    clickTag(MenuScenarioDialogTestTags.PLAY_SCENARIO_BUTTON)
}
