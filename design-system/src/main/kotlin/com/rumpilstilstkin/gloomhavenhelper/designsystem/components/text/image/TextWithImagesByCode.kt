package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.image

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun TextWithImagesByCode(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    textAlign: TextAlign? = null,
) {
    val textWithIcons = replaceTextWithIcons(text)
    val inlineContentMap = rememberIconsInlineContent(fontSize = style.fontSize)
    Text(
        modifier = modifier,
        text = textWithIcons,
        inlineContent = inlineContentMap,
        color = color,
        style = style,
        textAlign = textAlign,
    )
}

@Preview
@Composable
private fun TextWithImagesByCodePreview() {
    GloomhavenMasterTheme {
        TextWithImagesByCode(text = "Remove two cards #40")
    }
}
