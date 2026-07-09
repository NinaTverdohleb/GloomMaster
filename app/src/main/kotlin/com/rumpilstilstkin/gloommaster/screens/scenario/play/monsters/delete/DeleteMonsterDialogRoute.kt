package com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.delete

import androidx.compose.runtime.Composable
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract

object DeleteMonsterDialogContract : OverlayContract<DeleteMonsterDialogInput, Unit> {
    @Composable
    override fun Content(
        input: DeleteMonsterDialogInput,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        DeleteMonsterDialog(
            monsterName = input.monsterName,
            delete = { onDismissWithResult(Unit) },
            close = { onDismissWithResult(null) },
        )
    }
}
