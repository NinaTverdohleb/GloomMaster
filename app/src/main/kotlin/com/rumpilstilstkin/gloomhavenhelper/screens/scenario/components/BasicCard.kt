package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.CardColors

@Composable
fun BasicCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) =
    Card(
        modifier = modifier,
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }