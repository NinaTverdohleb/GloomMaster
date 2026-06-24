package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter.GloomCounterFull
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomNumberPicker

@Composable
fun GoldRow(
    goldCount: Int,
    modifier: Modifier = Modifier,
    goldRange: IntRange = 0..1000000,
    onGoldChanged: (Int) -> Unit,
) = GloomCard(
    modifier = modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                style = MaterialTheme.typography.headlineSmall,
                text = stringResource(R.string.gold_title),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        GloomCounterFull(
            value = goldCount,
            intRange = goldRange,
            onValueChange = onGoldChanged,
        )
    }
}

@Preview
@Composable
private fun GoldRowPreview() {
    GloomhavenMasterTheme {
        GoldRow(
            goldCount = 34,
            onGoldChanged = {},
        )
    }
}
