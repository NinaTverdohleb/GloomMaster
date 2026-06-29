package com.rumpilstilstkin.gloomhavenhelper.benchmark.steps

import androidx.benchmark.macro.MacrobenchmarkScope
import com.rumpilstilstkin.gloomhavenhelper.benchmark.AppTags
import com.rumpilstilstkin.gloomhavenhelper.benchmark.TestConsts
import com.rumpilstilstkin.gloomhavenhelper.benchmark.clickTag
import com.rumpilstilstkin.gloomhavenhelper.benchmark.waitForTag

fun MacrobenchmarkScope.createTeam() {
    clickTag(AppTags.EmptyTeam.START_BUTTON)

    val nameField = waitForTag(AppTags.AddTeamDialog.NAME_FIELD)
    nameField.click()
    nameField.text = TestConsts.TEAM_NAME
    device.waitForIdle()

    clickTag(AppTags.AddTeamDialog.ADD_BUTTON)
}
