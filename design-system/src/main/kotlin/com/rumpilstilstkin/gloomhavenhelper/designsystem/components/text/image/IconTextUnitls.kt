package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.image

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.game.GameIcon

fun replaceTextWithIcons(text: String): AnnotatedString {
    val pattern =
        Regex("#(\\d+)")
    val matches = pattern.findAll(text)

    return buildAnnotatedString {
        var currentIndex = 0
        for (match in matches) {
            append(text.substring(currentIndex, match.range.first))
            withStyle(style = SpanStyle()) {
                appendInlineContent(match.groupValues[1])
            }
            currentIndex = match.range.last + 1
        }
        append(text.substring(currentIndex))
    }
}

@Composable
fun rememberIconsInlineContent(fontSize: TextUnit): Map<String, InlineTextContent> {
    val placeholderSize = fontSize * 1.3f

    return remember(fontSize) {
        GameIcon.entries.associate { icon ->
            icon.textCode.value to
                InlineTextContent(
                    Placeholder(
                        width = placeholderSize,
                        height = placeholderSize,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
                    ),
                ) {
                    val modifier = Modifier.fillMaxSize()

                    Icon(
                        painter = icon.image.painter(),
                        contentDescription = stringResource(id = icon.titleRes),
                        tint = icon.color,
                        modifier = modifier,
                    )
                }
        }
    }
}
