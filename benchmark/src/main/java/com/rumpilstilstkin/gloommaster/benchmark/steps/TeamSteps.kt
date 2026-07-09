package com.rumpilstilstkin.gloommaster.benchmark.steps

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.Direction
import com.rumpilstilstkin.gloommaster.benchmark.TestConsts
import com.rumpilstilstkin.gloommaster.benchmark.clickTag
import com.rumpilstilstkin.gloommaster.benchmark.clickTagRepeated
import com.rumpilstilstkin.gloommaster.benchmark.existTag
import com.rumpilstilstkin.gloommaster.benchmark.pressBackAndIdle
import com.rumpilstilstkin.gloommaster.benchmark.waitForTag
import com.rumpilstilstkin.gloommaster.testtags.components.CounterTestTags
import com.rumpilstilstkin.gloommaster.testtags.screens.start.EmptyTeamScreenTestTags
import com.rumpilstilstkin.gloommaster.testtags.screens.start.team.TeamTabScreenTestTags
import com.rumpilstilstkin.gloommaster.testtags.screens.start.team.component.AchievementTestTags
import com.rumpilstilstkin.gloommaster.testtags.screens.start.team.component.TeamProsperityTestTags
import com.rumpilstilstkin.gloommaster.testtags.screens.start.team.component.TeamReputationTestTags
import com.rumpilstilstkin.gloommaster.testtags.screens.teem.achievement.AchievementsScreenTestTags
import com.rumpilstilstkin.gloommaster.testtags.screens.teem.create.AddTeamDialogTestTags

fun MacrobenchmarkScope.createTeamIfNeed() {
    if (existTag(EmptyTeamScreenTestTags.START_BUTTON)) {
        clickTag(EmptyTeamScreenTestTags.START_BUTTON)

        val nameField = waitForTag(AddTeamDialogTestTags.NAME_FIELD)
        nameField.click()
        nameField.text = TestConsts.TEAM_NAME
        device.waitForIdle()

        clickTag(AddTeamDialogTestTags.ADD_BUTTON)
    }
}

fun MacrobenchmarkScope.exerciseTeamTab(count: Int) {
    while (!existTag(TeamReputationTestTags.COUNTER)) {
        waitForTag(TeamTabScreenTestTags.ROOT_COLUMN).scroll(Direction.UP, 0.8f)
    }
    clickTagRepeated(TeamReputationTestTags.COUNTER, CounterTestTags.TEST_TAG_PLUS, times = count)
    clickTagRepeated(TeamReputationTestTags.COUNTER, CounterTestTags.TEST_TAG_MINUS, times = count)

    // Prosperity counter + donate.
    clickTagRepeated(TeamProsperityTestTags.COUNTER, CounterTestTags.TEST_TAG_PLUS, times = count)
    clickTagRepeated(TeamProsperityTestTags.COUNTER, CounterTestTags.TEST_TAG_MINUS, times = count)

    // Global achievements → screen → add one → back.
    while (!existTag(AchievementTestTags.GLOBAL_BLOCK)) {
        waitForTag(TeamTabScreenTestTags.ROOT_COLUMN).scroll(Direction.DOWN, 0.8f)
    }
    clickTag(AchievementTestTags.GLOBAL_BLOCK)
    pressBackAndIdle(
        AchievementsScreenTestTags.ADD_FAB
    )

    // Team achievements → screen → add one → back.
    while (!existTag(AchievementTestTags.TEAM_BLOCK)) {
        waitForTag(TeamTabScreenTestTags.ROOT_COLUMN).scroll(Direction.DOWN, 0.8f)
    }
    clickTag(AchievementTestTags.TEAM_BLOCK)
    pressBackAndIdle(
        AchievementsScreenTestTags.ADD_FAB
    )
}
