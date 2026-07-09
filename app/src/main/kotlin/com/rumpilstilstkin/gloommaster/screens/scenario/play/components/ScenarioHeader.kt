package com.rumpilstilstkin.gloommaster.screens.scenario.play.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.components.GloomCard
import com.rumpilstilstkin.gloommaster.designsystem.icons.ActionIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.Magic
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ChargeLevel
import com.rumpilstilstkin.gloommaster.screens.scenario.play.state.toIcon
import com.rumpilstilstkin.gloommaster.testtags.screens.scenario.play.components.ScenarioHeaderTestTags
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf

@Composable
internal fun ScenarioHeader(
    trapDamage: Int,
    magics: ImmutableMap<Magic, ChargeLevel>,
    showStats: () -> Unit,
    modifier: Modifier = Modifier,
    clickMagic: (magic: Magic) -> Unit,
) = Row(
    modifier =
        modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
) {
    val assets = rememberChargeIconAssets()
    FlowRow(
        modifier =
            Modifier
                .fillMaxWidth()
                .weight(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp),
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
                        ) { clickMagic(magic) }
                        .testTag(ScenarioHeaderTestTags.magic(magic.name)),
                icon = magic.toIcon(),
                assets = assets,
                charge = charge ?: ChargeLevel.Zero,
            )
        }
    }
    GloomCard(
        modifier = Modifier.clickable { showStats() },
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
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
    }
}

@Preview
@Composable
private fun ScenarioHeaderPreview() {
    GloomhavenMasterTheme {
        ScenarioHeader(
            modifier = Modifier.fillMaxWidth(),
            trapDamage = 3,
            magics =
                persistentMapOf(
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
