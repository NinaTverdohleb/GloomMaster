package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun GloomRoundLabel(label: String) =
    Box(
        modifier =
            Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier,
            style = MaterialTheme.typography.headlineSmall,
            text = label,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }

@Preview
@Composable
private fun GloomItemRightItemComponentsPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GloomRoundLabel(
                label = "5",
            )
        }
    }
}
