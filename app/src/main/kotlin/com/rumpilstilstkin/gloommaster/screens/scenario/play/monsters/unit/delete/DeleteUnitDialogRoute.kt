package com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.unit.delete

import androidx.compose.runtime.Composable
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract

object DeleteUnitDialogContract : OverlayContract<DeleteUnitDialogInput, Unit> {
    @Composable
    override fun Content(
        input: DeleteUnitDialogInput,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        DeleteUnitDialog(
            monsterName = input.monsterName,
            unitNumber = input.unitNumber,
            delete = { onDismissWithResult(Unit) },
            close = { onDismissWithResult(null) },
        )
    }
}
