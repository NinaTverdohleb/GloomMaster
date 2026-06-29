package com.rumpilstilstkin.gloomhavenhelper.benchmark

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.Direction
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.addMonster
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.addMonsterUnits
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.back
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.changeMagicCharge
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.changeUnitLife
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.createTeam
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.nextRound
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.openAndPlayScenario
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.toggleUnitEffect
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * End-to-end interactive benchmark for the scenario play flow.
 *
 * Flow (see plans/play_test.md):
 * open app → create team → open active scenario → Play Scenario →
 * add monster + unit → round → toggle effect → change life → round →
 * change magic charge → round.
 *
 * Each step is an extension on [androidx.benchmark.macro.MacrobenchmarkScope] in
 * `TeamSteps.kt` / `ScenarioSteps.kt`, locating elements via `By.res(<testTag>)`.
 * Tags are exposed to UiAutomator by `testTagsAsResourceId = true` (root Surface in
 * MainActivity + inside GloomBasicDialog) and mirrored in [AppTags].
 *
 * `iterations = 1` is temporary while the flow stabilizes.
 *
 * Run with:
 *   ./gradlew :benchmark:connectedBenchmarkBenchmarkAndroidTest \
 *     -Pandroid.testInstrumentationRunnerArguments.class=com.rumpilstilstkin.gloomhavenhelper.benchmark.ScenarioPlayBenchmark
 */
@RunWith(AndroidJUnit4::class)
class ScenarioPlayBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scenarioPlayFlow() {
        var isFirstIteration = true
        benchmarkRule.measureRepeated(
            packageName = TestConsts.TARGET_PACKAGE,
            metrics = listOf(FrameTimingMetric()),
            compilationMode = CompilationMode.Partial(
                baselineProfileMode = BaselineProfileMode.Require,
            ),
            iterations = 15,
            setupBlock = {
                pressHome()
                startActivityAndWait()
                if (isFirstIteration) {
                    createTeam()
                    isFirstIteration = false
                } else {
                    waitForTag(AppTags.TeamTabScreen.ROOT_COLUMN).scroll(Direction.DOWN, 0.8f)
                }
            },
        ) {
            openAndPlayScenario()

            addMonster()
            addMonsterUnits()

            nextRound()

            toggleUnitEffect()
            changeUnitLife()

            nextRound()

            changeMagicCharge()

            nextRound()

            back()
        }
    }
}
