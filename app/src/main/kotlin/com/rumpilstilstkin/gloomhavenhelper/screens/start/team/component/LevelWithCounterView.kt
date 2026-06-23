package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter.GloomCounterFull
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomRoundLabel
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun LevelWithCounterView(
    label: String,
    level: String,
    counterValue: Int,
    showSign: Boolean,
    range: IntRange,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GloomRoundLabel(
                label = level
            )
            Text(
                style = MaterialTheme.typography.headlineSmall,
                text = label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        GloomCounterFull(
            value = counterValue,
            showSign = showSign,
            intRange = range,
            onValueChange = onValueChange
        )
    }
}

@Preview
@Composable
private fun TeamProsperitySample() {
    GloomhavenMasterTheme {
        LevelWithCounterView(
            label = "Label",
            level = "-1",
            counterValue = 2,
            showSign = true,
            range = IntRange(-20, 20),
            onValueChange = {}
        )
    }
}