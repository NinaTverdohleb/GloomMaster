package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog

@Composable
internal fun ScenarioStatsDialog(
    level: Int,
    exp: Int,
    gold: Int,
    trapDamage: Int,
    onDismiss: () -> Unit,
) {
    GloomAlertDialog(
        title = stringResource(R.string.scenario_stats_title),
        onDismissRequest = onDismiss,
        onConfirmRequest = null,
    ) {
        StatItem(
            value = stringResource(R.string.stat_monster_level, level),
            image = R.drawable.ic_level,
        )

        StatItem(
            value = stringResource(R.string.stat_exp_reward, exp),
            image = R.drawable.ic_exp,
        )

        StatItem(
            value = stringResource(R.string.stat_gold_reward, gold),
            image = R.drawable.ic_gold,
        )

        StatItem(
            value = stringResource(R.string.stat_trap_damage, trapDamage),
            image = R.drawable.ic_gold,
        )
    }
}

@Preview
@Composable
private fun ScenarioStatsDialogPreview() {
    GloomhavenMasterTheme {
        ScenarioStatsDialog(
            level = 1,
            exp = 100,
            gold = 100,
            trapDamage = 10,
            onDismiss = { },
        )
    }
}
