package com.rumpilstilstkin.gloomhavenhelper.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun RoundButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
    size: GloomSize = GloomSize.M,
    onClick: () -> Unit
) {
    val mutableInteractionSource = remember {
        MutableInteractionSource()
    }
    val boxSize = when(size){
        GloomSize.S -> 32.dp
        GloomSize.M -> 48.dp
    }
    Box(
        modifier = modifier
            .size(boxSize)
            .clickable(interactionSource = mutableInteractionSource, indication = null) {
                onClick.invoke()
            }
            .background(
                color = color,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier,
            style = MaterialTheme.typography.headlineMedium,
            text = text,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview
@Composable
private fun RoundButtonPreview() {
    GloomhavenHalperTheme {
        RoundButton(
            text = "2",
            onClick = {}
        )
    }
}