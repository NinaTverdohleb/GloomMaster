package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.CounterRightItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.RightItemChecker
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.RightItemIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.RightItemNumber
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.RightItemText
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun GloomHeader(
    text: String,
    modifier: Modifier = Modifier
) = Text(
    modifier = modifier,
    text = text,
    style = MaterialTheme.typography.titleLarge,
    color = MaterialTheme.colorScheme.primary,
)

@Composable
fun GloomHeaderVariant(
    text: String,
    modifier: Modifier = Modifier
) = Text(
    modifier = modifier,
    text = text,
    style = MaterialTheme.typography.bodyMedium,
    color = MaterialTheme.colorScheme.primaryContainer,
)


@Preview
@Composable
private fun GloomHeaderPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            GloomHeader("Header")
            GloomHeaderVariant("Header")
        }
    }
}