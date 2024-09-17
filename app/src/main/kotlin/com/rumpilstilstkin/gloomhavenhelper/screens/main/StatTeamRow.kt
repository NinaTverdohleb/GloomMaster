package com.rumpilstilstkin.gloomhavenhelper.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun StatTeamRow(
    reputation: Int,
    prosperity: Int,
    modifier: Modifier = Modifier,
    onReputationClick: () -> Unit = {},
    onProsperityClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.padding(vertical = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .clickable {
                    onReputationClick.invoke()
                },
            text = "Репутация: $reputation",
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .clickable {
                    onProsperityClick.invoke()
                },
            text = "Процветание: $prosperity",
            textAlign = TextAlign.Center
        )

    }
}

