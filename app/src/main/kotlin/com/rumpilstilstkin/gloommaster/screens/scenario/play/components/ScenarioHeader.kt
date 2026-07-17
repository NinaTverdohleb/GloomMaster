package com.rumpilstilstkin.gloommaster.screens.scenario.play.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineFilledButton
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineFilledButtonIconVariant
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.Magic
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ChargeLevel
import com.rumpilstilstkin.gloommaster.screens.scenario.play.state.toIcon
import com.rumpilstilstkin.gloommaster.testtags.screens.scenario.play.PlayScenarioScreenTestTags
import com.rumpilstilstkin.gloommaster.testtags.screens.scenario.play.components.ScenarioHeaderTestTags
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf

@Composable
internal fun ScenarioHeader(
    magics: ImmutableMap<Magic, ChargeLevel>,
    modifier: Modifier = Modifier,
    clickMagic: (magic: Magic) -> Unit,
    nextRound: () -> Unit,
) = Column(
    modifier =
        modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    val assets = rememberChargeIconAssets()
    FlowRow(
        modifier =
            Modifier
                .fillMaxWidth(),
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
    GloomOutlineFilledButton(
        modifier =
            Modifier
                .fillMaxWidth()
                .testTag(PlayScenarioScreenTestTags.ROUND_BUTTON),
        icon = AppIcon.Play,
        text = stringResource(R.string.round_label),
        onClick = nextRound,
    )
}

@Preview
@Composable
private fun ScenarioHeaderPreview() {
    GloomhavenMasterTheme {
        ScenarioHeader(
            modifier = Modifier.fillMaxWidth(),
            magics =
                persistentMapOf(
                    Magic.FIRE to ChargeLevel.Zero,
                    Magic.FROST to ChargeLevel.Two,
                    Magic.AIR to ChargeLevel.Zero,
                    Magic.EARTH to ChargeLevel.Two,
                    Magic.SUN to ChargeLevel.One,
                    Magic.MOON to ChargeLevel.Two,
                ),
            clickMagic = {},
            nextRound = {},
        )
    }
}
