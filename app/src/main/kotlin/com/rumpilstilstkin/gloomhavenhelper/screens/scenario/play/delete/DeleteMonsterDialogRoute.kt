package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.delete

import androidx.compose.runtime.Composable
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract

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
