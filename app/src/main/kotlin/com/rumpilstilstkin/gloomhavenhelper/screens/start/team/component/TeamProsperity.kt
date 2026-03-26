package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomSize
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NumberPickerProgress
import com.rumpilstilstkin.gloomhavenhelper.ui.components.PickerButton
import com.rumpilstilstkin.gloomhavenhelper.ui.components.PickerButtonType
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
internal fun TeamProsperity(
    prosperity: Prosperity,
    churchValue: Int,
    churchValueForNextProsperity: Int,
    modifier: Modifier = Modifier,
    updateProsperity: (Int) -> Unit,
    donate: () -> Unit
) = GloomCard(
    modifier = modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                style = MaterialTheme.typography.labelLarge,
                text = "ПРОЦВЕТАНИЕ",
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                style = MaterialTheme.typography.labelMedium,
                text = "Уровень: ${prosperity.prosperityLevel}"
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        NumberPickerProgress(
            value = prosperity.prosperityLevelValue,
            showSign = false,
            intRange = prosperity.prosperityRange
        ) { newValue ->
            updateProsperity(newValue)
        }
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelLarge,
            text = "Пожертвования",
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = churchValue.toString(),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            PickerButton(
                size = GloomSize.M,
                value = churchValue,
                type = PickerButtonType.PLUS,
                onValueChange = { donate() }
            )

            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "Следующее повышение процветания при $churchValueForNextProsperity",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End
            )
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
            donate = {}
        )
    }
}

