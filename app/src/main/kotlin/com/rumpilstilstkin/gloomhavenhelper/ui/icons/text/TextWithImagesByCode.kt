package com.rumpilstilstkin.gloomhavenhelper.ui.icons.text

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun TextWithImagesByCode(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    fontSize: TextUnit = 18.sp,
    textAlign: TextAlign? = null,
) {
    val textWithIcons = replaceTextWithIcons(text)
    val inlineContentMap = rememberIconsInlineContent(fontSize = fontSize)
    Text(
        modifier = modifier,
        text = textWithIcons,
        inlineContent = inlineContentMap,
        color = color,
        fontSize = fontSize,
        textAlign = textAlign
    )
}

@Preview
@Composable
private fun TextWithImagesByCodePreview() {
    GloomhavenMasterTheme {
        TextWithImagesByCode(text = "Remove two cards #40")
    }
}