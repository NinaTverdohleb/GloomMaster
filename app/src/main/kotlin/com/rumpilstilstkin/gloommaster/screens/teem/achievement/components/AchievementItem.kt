package com.rumpilstilstkin.gloommaster.screens.teem.achievement.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloommaster.designsystem.components.items.CounterRightItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.AchievementWithName

@Composable
fun AchievementItem(
    achievement: AchievementWithName,
    changeValue: (Int) -> Unit,
) = GloomListFilledItem(
    title = achievement.name,
    rightComponent = {
        if (achievement.maxValue > 1) {
            CounterRightItem(
                value = achievement.value,
                intRange = 1..achievement.maxValue,
                onValueChange = changeValue,
            )
        }
    },
)

@Preview
@Composable
private fun AchievementItemPreview() {
    GloomhavenMasterTheme {
        AchievementItem(
            achievement = AchievementWithName.fixture(),
            changeValue = {},
        )
    }
}
