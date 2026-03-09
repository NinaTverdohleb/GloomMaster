package com.rumpilstilstkin.gloomhavenhelper.ui.icons.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun TextWithImagesByCode(
    text: String,
    modifier: Modifier = Modifier
) {
    val textWithIcons = replaceTextWithIcons(text)
    Text(
        modifier = modifier,
        text = textWithIcons,
        inlineContent = iconsInlineContentMap,
        color = MaterialTheme.colorScheme.onSurface,
        lineHeight = 28.sp
    )
}

@Preview
@Composable
private fun TextWithImagesByCodePreview() {
    GloomhavenHalperTheme {
        TextWithImagesByCode(text = "Уберите две карты #27")
    }
}