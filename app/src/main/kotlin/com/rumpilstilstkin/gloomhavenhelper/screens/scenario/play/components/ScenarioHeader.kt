package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomListOutlineItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.LeftItemNumber
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.ActionIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ChargeLevel
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.toIcon

@Composable
internal fun ScenarioHeader(
    scenarioNumber: Int?,
    scenarioName: String,
    location: String?,
    trapDamage: Int,
    magics: Map<Magic, ChargeLevel>,
    showStats: () -> Unit,
    modifier: Modifier = Modifier,
    clickMagic: (magic: Magic) -> Unit,
) = Column(
    modifier =
        modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(24.dp),
) {
    GloomListOutlineItem(
        title = scenarioName,
        description = location,
        onClick = showStats,
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
        rightComponent = {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = trapDamage.toString(),
                    style =
                        MaterialTheme.typography.titleLarge.copy(),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = ActionIcon.Trap.painter(),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        magics.keys.forEach { magic ->
            val charge = magics[magic]
            ChargeIcon(
                modifier =
                    Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { clickMagic(magic) },
                icon = magic.toIcon(),
                charge = charge ?: ChargeLevel.Zero,
            )
        }
    }
}

@Preview
@Composable
private fun ScenarioHeaderPreview() {
    GloomhavenMasterTheme {
        ScenarioHeader(
            modifier = Modifier.fillMaxWidth(),
            scenarioNumber = 99,
            scenarioName = "Name",
            location = "Bad place",
            trapDamage = 3,
            magics =
                mapOf(
                    Magic.FIRE to ChargeLevel.Zero,
                    Magic.FROST to ChargeLevel.Two,
                    Magic.AIR to ChargeLevel.Zero,
                    Magic.EARTH to ChargeLevel.Two,
                    Magic.SUN to ChargeLevel.One,
                    Magic.MOON to ChargeLevel.Two,
                ),
            showStats = {},
            clickMagic = {},
        )
    }
}
