package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NumberPickerProgress
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
internal fun TeamProsperity(
    prosperity: Prosperity,
    modifier: Modifier = Modifier,
    updateProsperity: (Int) -> Unit
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
    }
}

@Preview
@Composable
private fun TeamProsperitySample() {
    GloomhavenHalperTheme {
        TeamProsperity(
            prosperity = Prosperity.fixture(),
            updateProsperity = {}
        )
    }
}

