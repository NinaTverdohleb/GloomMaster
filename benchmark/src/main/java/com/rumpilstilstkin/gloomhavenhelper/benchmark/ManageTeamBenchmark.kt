package com.rumpilstilstkin.gloomhavenhelper.benchmark

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
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.createTeamIfNeed
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.exerciseCharactersTab
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.exerciseScenariosTab
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.exerciseShopTab
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.exerciseTeamTab
import com.rumpilstilstkin.gloomhavenhelper.benchmark.steps.openTabBottom
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ManageTeamBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun startTabsFlow() = benchmarkRule.measureRepeated(
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
            createTeamIfNeed()
        },
    ) {
        openTabBottom(0)
        exerciseTeamTab(15)
        openTabBottom(1)
        exerciseCharactersTab(5)
        openTabBottom(2)
        exerciseShopTab()
        openTabBottom(3)
        exerciseScenariosTab(5)
    }
}
