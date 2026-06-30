package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter.GloomCountButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter.GloomGloomCounterProgress
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter.PickerButtonType
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.start.team.component.TeamProsperityTestTags

@Composable
internal fun TeamProsperity(
    prosperity: Prosperity,
    churchValue: Int,
    churchValueForNextProsperity: Int,
    modifier: Modifier = Modifier,
    updateProsperity: (Int) -> Unit,
    donate: () -> Unit,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(20.dp),
) {
    GloomHeader(
        stringResource(R.string.prosperity),
    )
    GloomCard(
        modifier = modifier,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                LevelWithCounterView(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .testTag(TeamProsperityTestTags.COUNTER),
                    label = stringResource(R.string.level_label),
                    level = prosperity.prosperityLevel.toString(),
                    showSign = false,
                    range = prosperity.prosperityRange,
                    counterValue = prosperity.prosperityLevelValue,
                ) { newValue ->
                    updateProsperity(newValue)
                }

                GloomGloomCounterProgress(
                    value = prosperity.prosperityLevelValue,
                    intRange = prosperity.prosperityRange,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Spacer(
                modifier = Modifier.height(32.dp),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = stringResource(R.string.donations),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        style = MaterialTheme.typography.bodySmall,
                        text =
                            stringResource(
                                R.string.next_prosperity_at,
                                churchValueForNextProsperity,
                            ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Spacer(Modifier.width(24.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text(
                        text = churchValue.toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    GloomCountButton(
                        modifier = Modifier.testTag(TeamProsperityTestTags.DONATE),
                        value = churchValue,
                        type = PickerButtonType.PLUS,
                        onValueChange = { donate() },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TeamProsperitySample() {
    GloomhavenMasterTheme {
        TeamProsperity(
            prosperity = Prosperity.fixture(),
            churchValue = 100,
            churchValueForNextProsperity = 150,
            updateProsperity = {},
            donate = {},
        )
    }
}
