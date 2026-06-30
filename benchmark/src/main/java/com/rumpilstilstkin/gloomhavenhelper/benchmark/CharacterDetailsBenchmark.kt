package com.rumpilstilstkin.gloomhavenhelper.benchmark

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MemoryUsageMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.back
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.createTeamIfNeed
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.exerciseGeneralTab
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.exerciseStuffTab
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.openCharacterDetails
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.openTabBottom
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.openTabTop
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterDetailsBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun characterDetailsFlow() = benchmarkRule.measureRepeated(
        packageName = TestConsts.TARGET_PACKAGE,
        metrics = listOf(FrameTimingMetric(), MemoryUsageMetric(MemoryUsageMetric.Mode.Max)),
        compilationMode = CompilationMode.Partial(
            baselineProfileMode = BaselineProfileMode.Require,
        ),
        iterations = 10,
        setupBlock = {
            pressHome()
            startActivityAndWait()
            createTeamIfNeed()
            openTabBottom(1)
        },
    ) {
        openCharacterDetails()
        exerciseGeneralTab(30)
        exerciseStuffTab()
        repeat(10) {
            openTabTop(2)
            openTabTop(1)
            openTabTop(0)
        }
        back()
    }
}
