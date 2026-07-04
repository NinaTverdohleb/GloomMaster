package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomListItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.LeftItemIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.LeftItemNumber
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.RightItemLabel
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GameStatIcons
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
internal fun ScenarioStatsDialog(
    scenarioNumber: Int?,
    scenarioName: String,
    location: String?,
    level: Int,
    exp: Int,
    gold: Int,
    trapDamage: Int,
    onDismiss: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        GloomListItem(
            title = scenarioName.ifBlank { stringResource(R.string.custom_scenario) },
            description = location,
            leftComponent = {
                scenarioNumber?.let {
                    LeftItemNumber(
                        number =
                            stringResource(
                                R.string.scenario_number_format,
                                scenarioNumber.toString(),
                            ),
                    )
                }
            },
        )

        GloomListItem(
            title = stringResource(R.string.stat_monster_level),
            leftComponent = {
                LeftItemIcon(
                    icon = GameStatIcons.Level,
                )
            },
            rightComponent = {
                RightItemLabel(
                    text = level.toString(),
                )
            },
        )
        GloomListItem(
            title = stringResource(R.string.stat_exp_reward),
            leftComponent = {
                LeftItemIcon(
                    icon = GameStatIcons.Exp,
                )
            },
            rightComponent = {
                RightItemLabel(
                    text = exp.toString(),
                )
            },
        )

        GloomListItem(
            title = stringResource(R.string.stat_gold_reward),
            leftComponent = {
                LeftItemIcon(
                    icon = GameStatIcons.Gold,
                )
            },
            rightComponent = {
                RightItemLabel(
                    text = gold.toString(),
                )
            },
        )

        GloomListItem(
            title = stringResource(R.string.stat_trap_damage),
            leftComponent = {
                LeftItemIcon(
                    icon = GameStatIcons.Trap,
                )
            },
            rightComponent = {
                RightItemLabel(
                    text = trapDamage.toString(),
                )
            },
        )

        Spacer(Modifier.height(8.dp))

        GloomOutlineButton(
            icon = AppIcon.Check,
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.ok),
            onClick = onDismiss,
        )
    }
}

@Preview
@Composable
private fun ScenarioStatsDialogPreview() {
    GloomhavenMasterTheme {
        Box(
            modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    .padding(16.dp),
        ) {
            ScenarioStatsDialog(
                scenarioNumber = 99,
                scenarioName = "Name",
                location = "Bad place",
                level = 1,
                exp = 100,
                gold = 100,
                trapDamage = 10,
                onDismiss = { },
            )
        }
    }
}
