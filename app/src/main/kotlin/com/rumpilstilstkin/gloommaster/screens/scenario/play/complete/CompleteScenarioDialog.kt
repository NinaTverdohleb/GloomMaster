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
import com.rumpilstilstkin.gloommaster.designsystem.components.items.RightItemLabel
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.icons.GameStatIcons
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme

@Composable
internal fun CompleteScenarioDialog(
    exp: Int,
    gold: Int,
    complete: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
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

        Spacer(Modifier.height(8.dp))

        GloomOutlineButton(
            icon = AppIcon.Check,
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.complete_scenario),
            onClick = complete,
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
                complete = { },
            )
        }
    }
}
