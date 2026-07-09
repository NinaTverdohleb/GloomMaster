package com.rumpilstilstkin.gloommaster.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
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

fun MacrobenchmarkScope.existTag( tag: String): Boolean{
    val currentObj = device.wait(
        Until.findObject(By.res(tag)),
        TestConsts.UI_TIMEOUT
    )
    return currentObj != null
}


/** Waits for [tags], clicks it, and idles. */
fun MacrobenchmarkScope.clickTag(vararg tags: String) {
    waitForTag(*tags).click()
    device.waitForIdle()
}

/** Clicks [tag] [times] times (e.g. to pump a +/- counter so the run lasts longer). */
fun MacrobenchmarkScope.clickTagRepeated(vararg tags: String, times: Int) {
    repeat(times) {
        clickTag(*tags)
    }
}

/** Swipes left on the element with [tag] to reveal a swipeable row's action menu. */
fun MacrobenchmarkScope.swipeLeftOnTag(tag: String): Boolean {
    val obj = device.findObject(By.res(tag)) ?: return false
    obj.swipe(Direction.LEFT, 0.9f, 400)
    device.waitForIdle()
    return true
}

/**
 * Presses the system back button and idles.
 *
 * `waitForIdle()` can return while a screen or dialog is still committing its
 * entrance, so a back that lands in that window acts on the previous screen —
 * dismissing one level too many and leaving the next step on the wrong screen
 * (e.g. a later `clickTag` then can't find its tag).
 *
 * Pass [anchorTag] — a testTag on the screen being dismissed — to make back
 * deterministic: we wait for that element to be present (entrance committed),
 * press back, then wait for it to be gone (exit committed) before returning. With
 * no anchor we fall back to a plain idle-then-back for callers where the closing
 * screen has no stable tag.
 */
fun MacrobenchmarkScope.pressBackAndIdle(anchorTag: String) {
    waitForTag(anchorTag)                       // entrance fully committed
    device.pressBack()
    device.wait(Until.gone(By.res(anchorTag)), TestConsts.UI_TIMEOUT)
    device.waitForIdle()
}
