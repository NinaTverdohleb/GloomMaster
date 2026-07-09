package com.rumpilstilstkin.gloommaster.screens.scenario.play.stats

import androidx.compose.runtime.Composable
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract

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
            scenarioNumber = input.scenarioNumber,
            scenarioName = input.scenarioName,
            location = input.location,
            trapDamage = input.trapDamage,
            onDismiss = { onDismissWithResult(Unit) },
        )
    }
}
