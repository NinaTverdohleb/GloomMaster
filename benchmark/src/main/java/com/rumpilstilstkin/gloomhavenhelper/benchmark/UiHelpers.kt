package com.rumpilstilstkin.gloomhavenhelper.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

/**
 * Shared UiAutomator helpers for the scenario benchmark steps.
 *
 * All lookups go through [waitForTag] so we always take the object returned by
 * `wait(...)` (never a separate `findObject`, which can race and return null).
 */

/** Waits for the element with [tags] (a `By.res` resource-id) and returns it, or fails. */
fun MacrobenchmarkScope.waitForTag(vararg tags: String): UiObject2 {
    require(tags.isNotEmpty()) { "Element testTag not set" }

    var currentObj = device.wait(
        Until.findObject(By.res(tags.first())),
        TestConsts.UI_TIMEOUT
    )
    checkNotNull(currentObj) { "Element with testTag '${tags.first()}' not found" }

    for (i in 1 until tags.size) {
        val nextTag = tags[i]
        currentObj = currentObj.wait(
            Until.findObject(By.res(nextTag)),
            TestConsts.UI_TIMEOUT
        )
        checkNotNull(currentObj) {
            "Element with testTag '$nextTag' not found in '${tags[i - 1]}'"
        }
    }

    return currentObj
}


/** Waits for [tags], clicks it, and idles. */
fun MacrobenchmarkScope.clickTag(vararg tags: String) {
    waitForTag(*tags).click()
    device.waitForIdle()
}
