package com.rumpilstilstkin.gloommaster.designsystem.components.stiker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme

enum class GloomStickerType {
    Top,
    Bottom,
}

@Composable
fun GloomStickerText(
    text: String,
    modifier: Modifier = Modifier,
    type: GloomStickerType = GloomStickerType.Top,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceBright,
    textColor: Color = MaterialTheme.colorScheme.outline,
    onClick: (() -> Unit)? = null,
) = GloomStickerContent(
    modifier = modifier,
    type = type,
    backgroundColor = backgroundColor,
    onClick = onClick,
    body = {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodySmall,
        )
    },
)

@Composable
fun GloomStickerContent(
    body: (@Composable BoxScope.() -> Unit),
    modifier: Modifier = Modifier,
    type: GloomStickerType = GloomStickerType.Top,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceBright,
    onClick: (() -> Unit)? = null,
) {
    val shape =
        when (type) {
            GloomStickerType.Top -> RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            GloomStickerType.Bottom -> RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
        }
    Box(
        modifier =
            modifier
                .clip(shape)
                .background(backgroundColor)
                .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        body()
    }
}

@Preview
@Composable
private fun GloomStickerTextPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GloomStickerText(
                text = "Action",
            )

            GloomStickerText(
                type = GloomStickerType.Bottom,
                text = "Action",
            )
        }
    }
}
