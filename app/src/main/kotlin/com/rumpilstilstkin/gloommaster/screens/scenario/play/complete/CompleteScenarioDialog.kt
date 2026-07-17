package com.rumpilstilstkin.gloommaster.screens.scenario.play.complete

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
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.LeftItemIcon
import com.rumpilstilstkin.gloommaster.designsystem.components.items.LeftItemNumber
import com.rumpilstilstkin.gloommaster.designsystem.components.items.RightItemLabel
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.icons.GameStatIcons
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme

@Composable
internal fun CompleteScenarioDialog(
    scenarioNumber: Int?,
    scenarioName: String,
    location: String?,
    level: Int,
    exp: Int,
    gold: Int,
    trapDamage: Int,
    complete: () -> Unit,
    dismiss: () -> Unit,
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
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.complete_scenario),
            onClick = complete,
        )

        GloomOutlineButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.close),
            onClick = dismiss,
        )
    }
}

@Preview
@Composable
private fun CompleteScenarioDialogPreview() {
    GloomhavenMasterTheme {
        Box(
            modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    .padding(16.dp),
        ) {
            CompleteScenarioDialog(
                exp = 100,
                gold = 100,
                scenarioNumber = 99,
                scenarioName = "Name",
                location = "Bad place",
                level = 1,
                trapDamage = 10,
                complete = { },
                dismiss = {},
            )
        }
    }
}
