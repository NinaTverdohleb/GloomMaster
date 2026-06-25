package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.stats

import androidx.compose.runtime.Composable
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract

object ScenarioStatsDialogContract : OverlayContract<ScenarioStatsDialogInput, Unit> {
    @Composable
    override fun Content(
        input: ScenarioStatsDialogInput,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        ScenarioStatsDialog(
            level = input.level,
            exp = input.exp,
            gold = input.gold,
            trapDamage = input.trapDamage,
            onDismiss = { onDismissWithResult(Unit) },
        )
    }
}
