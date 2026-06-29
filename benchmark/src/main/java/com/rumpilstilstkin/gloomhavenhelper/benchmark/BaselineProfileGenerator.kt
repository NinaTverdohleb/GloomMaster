package com.rumpilstilstkin.gloomhavenhelper.benchmark

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Generates a baseline profile for the app's startup path.
 *
 * Run with: `./gradlew :app:generateBaselineProfile`
 * The resulting profile is bundled into the app and consumed by [ExampleStartupBenchmark]
 * via `CompilationMode.Partial(BaselineProfileMode.Require)`.
 */
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() = baselineProfileRule.collect(
        packageName = "com.rumpilstilstkin.gloommaster"
    ) {
        pressHome()
        startActivityAndWait()
    }
}
