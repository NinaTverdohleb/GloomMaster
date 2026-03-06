package com.rumpilstilstkin.gloomhavenhelper.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun GloomCard(
    modifier: Modifier = Modifier,
    active: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) =
    Card(
        modifier = modifier,
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        border = BorderStroke(
            1.dp,
            if (active) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            content = content
        )
    }


@Preview
@Composable
private fun GloomCardPreview() {
    GloomhavenHalperTheme {
        GloomCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.height(40.dp)
            )
        }
    }
}