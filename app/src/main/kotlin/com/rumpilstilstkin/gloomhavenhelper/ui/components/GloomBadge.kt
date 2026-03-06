package com.rumpilstilstkin.gloomhavenhelper.ui.components

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun GloomBadge(
    text: String,
    modifier: Modifier = Modifier,
    size: GloomSize = GloomSize.S
) {
    val boxSize = when (size) {
        GloomSize.S -> 18.dp
        GloomSize.M -> 32.dp
    }
    val textSize = when (size) {
        GloomSize.S -> 10.sp
        GloomSize.M -> 18.sp
    }
    Box(
        modifier = modifier
            .size(boxSize)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = text,
            fontSize = textSize,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview
@Composable
private fun GloomBadgeSPreview() {
    GloomhavenHalperTheme {
        GloomBadge(
            text = "999",
            size = GloomSize.S
        )
    }
}

@Preview
@Composable
private fun GloomBadgeMPreview() {
    GloomhavenHalperTheme {
        GloomBadge(
            text = "999",
            size = GloomSize.M
        )
    }
}