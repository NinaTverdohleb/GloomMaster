package com.rumpilstilstkin.gloommaster.benchmark

import com.rumpilstilstkin.gloommaster.testtags.screens.scenario.play.PlayScenarioScreenTestTags

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MemoryUsageMetric
import androidx.benchmark.macro.PowerCategory
import androidx.benchmark.macro.PowerCategoryDisplayLevel
import androidx.benchmark.macro.PowerMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.Direction
import com.rumpilstilstkin.gloommaster.benchmark.steps.addMonster
import com.rumpilstilstkin.gloommaster.benchmark.steps.addMonsterUnits
import com.rumpilstilstkin.gloommaster.benchmark.steps.back
import com.rumpilstilstkin.gloommaster.benchmark.steps.changeMagicCharge
import com.rumpilstilstkin.gloommaster.benchmark.steps.changeUnitLife
import com.rumpilstilstkin.gloommaster.benchmark.steps.createTeamIfNeed
import com.rumpilstilstkin.gloommaster.benchmark.steps.nextRound
import com.rumpilstilstkin.gloommaster.benchmark.steps.openAndPlayScenario
import com.rumpilstilstkin.gloommaster.benchmark.steps.toggleUnitEffect
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioPlayBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun scenarioPlayFlow() {
        benchmarkRule.measureRepeated(
            packageName = TestConsts.TARGET_PACKAGE,
            metrics = listOf(
                FrameTimingMetric(),
                MemoryUsageMetric(MemoryUsageMetric.Mode.Max),
                PowerMetric(
                    PowerMetric.Type.Energy(
                        mapOf(
                            PowerCategory.CPU to PowerCategoryDisplayLevel.TOTAL,
                            PowerCategory.GPU to PowerCategoryDisplayLevel.TOTAL,
                            PowerCategory.DISPLAY to PowerCategoryDisplayLevel.TOTAL,
                            PowerCategory.MEMORY to PowerCategoryDisplayLevel.TOTAL,
                        ),
                    ),
                ),
            ),
            compilationMode = CompilationMode.Partial(
                baselineProfileMode = BaselineProfileMode.Require,
            ),
            iterations = 10,
            setupBlock = {
                pressHome()
                startActivityAndWait()
            },
        ) {
            createTeamIfNeed()

            openAndPlayScenario()

            addMonster()
            addMonsterUnits(0)
            addMonster()
            waitForTag(PlayScenarioScreenTestTags.MONSTER_PAGER).scroll(Direction.RIGHT, 0.8f)
            addMonsterUnits(1)

            repeat(10) {
                toggleUnitEffect(1)
                changeUnitLife(1)
            }
            nextRound()

            repeat(10) {
                changeUnitLife(0)
                nextRound()
            }

            repeat(10) {
                waitForTag(PlayScenarioScreenTestTags.MONSTER_PAGER).scroll(Direction.RIGHT, 0.8f)
                waitForTag(PlayScenarioScreenTestTags.MONSTER_PAGER).scroll(Direction.LEFT, 0.8f)
            }
            nextRound()
            repeat(10) {
                changeMagicCharge()
                nextRound()
            }

            back()
        }
    }
}
