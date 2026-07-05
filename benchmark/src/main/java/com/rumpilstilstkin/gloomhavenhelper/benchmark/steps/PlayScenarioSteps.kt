package com.rumpilstilstkin.gloomhavenhelper.benchmark.steps

import com.rumpilstilstkin.gloomhavenhelper.testtags.components.CounterTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.components.RightComponentsTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.scenario.play.PlayScenarioScreenTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.scenario.play.components.MonsterCardTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.scenario.play.components.RegularMonsterUnitTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.scenario.play.components.ScenarioHeaderTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.scenario.play.monsters.AddScenarioMonstersDialogTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.scenario.play.monsters.unit.add.AddMonsterUnitDialogTestTags

import androidx.benchmark.macro.MacrobenchmarkScope
import com.rumpilstilstkin.gloomhavenhelper.benchmark.clickTag
import com.rumpilstilstkin.gloomhavenhelper.benchmark.existTag

fun MacrobenchmarkScope.addMonster() {
    clickTag(PlayScenarioScreenTestTags.ADD_MONSTER_FAB)

    clickTag(PlayScenarioScreenTestTags.ADD_MONSTER_FAB_SCENARIO)

    clickTag(AddScenarioMonstersDialogTestTags.monster(0), RightComponentsTestTags.CHECKER)

    clickTag(AddScenarioMonstersDialogTestTags.ADD_BUTTON)
}

fun MacrobenchmarkScope.addMonsterUnits(index: Int) {
    clickTag(PlayScenarioScreenTestTags.card(index), MonsterCardTestTags.ADD_UNITS_BUTTON)
    clickTag(AddMonsterUnitDialogTestTags.unit(1))
    clickTag(AddMonsterUnitDialogTestTags.unit(2))
    clickTag(AddMonsterUnitDialogTestTags.unit(3))
    clickTag(AddMonsterUnitDialogTestTags.CONFIRM_BUTTON)
}

fun MacrobenchmarkScope.nextRound() {
    clickTag(PlayScenarioScreenTestTags.ROUND_BUTTON)
}

fun MacrobenchmarkScope.toggleUnitEffect(index: Int) {
    clickTag(
        PlayScenarioScreenTestTags.card(index),
        RegularMonsterUnitTestTags.effect(PlayScenarioConsts.EFFECT)
    )
}

fun MacrobenchmarkScope.changeUnitLife(index: Int) {
    repeat(10) {
        clickTag(PlayScenarioScreenTestTags.card(index), CounterTestTags.TEST_TAG_MINUS)
    }
    repeat(10) {
        clickTag(PlayScenarioScreenTestTags.card(index), CounterTestTags.TEST_TAG_PLUS)
    }
}

fun MacrobenchmarkScope.changeMagicCharge() {
    clickTag(ScenarioHeaderTestTags.magic(PlayScenarioConsts.MAGIC))
}

private object PlayScenarioConsts {
    const val EFFECT = "POISON"
    const val MAGIC = "FIRE"
}
