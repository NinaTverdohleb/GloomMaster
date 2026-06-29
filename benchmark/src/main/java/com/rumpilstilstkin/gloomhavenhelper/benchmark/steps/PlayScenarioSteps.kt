package com.rumpilstilstkin.gloomhavenhelper.benchmark.steps

import androidx.benchmark.macro.MacrobenchmarkScope
import com.rumpilstilstkin.gloomhavenhelper.benchmark.AppTags
import com.rumpilstilstkin.gloomhavenhelper.benchmark.AppTags.RightComponentsTestTags
import com.rumpilstilstkin.gloomhavenhelper.benchmark.clickTag

fun MacrobenchmarkScope.addMonster() {
    clickTag(AppTags.ScenarioScreen.ADD_MONSTER_FAB)

    clickTag(AppTags.AddScenarioMonstersDialog.monster(0), RightComponentsTestTags.CHECKER)

    clickTag(AppTags.AddScenarioMonstersDialog.ADD_BUTTON)
}

fun MacrobenchmarkScope.addMonsterUnits() {
    clickTag(AppTags.ScenarioScreen.card(0), AppTags.MonsterCard.ADD_UNITS_BUTTON)
    clickTag(AppTags.AddMonsterUnitDialog.unit(1))
    clickTag(AppTags.AddMonsterUnitDialog.unit(2))
    clickTag(AppTags.AddMonsterUnitDialog.unit(3))
    clickTag(AppTags.AddMonsterUnitDialog.OK_BUTTON)
}

fun MacrobenchmarkScope.nextRound() {
    clickTag(AppTags.ScenarioScreen.ROUND_BUTTON)
}

fun MacrobenchmarkScope.toggleUnitEffect() {
    clickTag(
        AppTags.ScenarioScreen.card(0),
        AppTags.MonsterUnit.effect(PlayScenarioConsts.EFFECT)
    )
}

fun MacrobenchmarkScope.changeUnitLife() {
    clickTag(AppTags.ScenarioScreen.card(0), AppTags.GloomCounter.TEST_TAG_MINUS)
    clickTag(AppTags.ScenarioScreen.card(0), AppTags.GloomCounter.TEST_TAG_PLUS)
}

fun MacrobenchmarkScope.changeMagicCharge() {
    clickTag(AppTags.ScenarioHeader.magic(PlayScenarioConsts.MAGIC))
}

private object PlayScenarioConsts{
    const val EFFECT = "POISON"
    const val MAGIC = "FIRE"
}
