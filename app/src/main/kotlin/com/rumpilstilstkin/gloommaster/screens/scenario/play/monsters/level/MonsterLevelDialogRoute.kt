package com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.level

import androidx.compose.runtime.Composable
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract

object MonsterLevelDialogContract : OverlayContract<MonsterLevelDialogInput, Int> {
    @Composable
    override fun Content(
        input: MonsterLevelDialogInput,
        onDismissWithResult: (Int?) -> Unit,
    ) {
        MonsterLevelDialog(
            unitLevel = input.unitLevel,
            unitNumber = input.unitNumber,
            monsterName = input.monsterName,
            changeLevel = { onDismissWithResult(it) },
        )
    }
}
