package com.rumpilstilstkin.gloomhavenhelper.ui.icons.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GameIcon

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
            icon.textCode.value to InlineTextContent(
                Placeholder(
                    width = placeholderSize,
                    height = placeholderSize,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                val modifier = Modifier.fillMaxSize()

                if (icon.color != null) {
                    Icon(
                        painter = painterResource(id = icon.imageRes),
                        contentDescription = "Icon",
                        tint = icon.color,
                        modifier = modifier
                    )
                } else {
                    Image(
                        painter = painterResource(id = icon.imageRes),
                        contentDescription = "Icon",
                        modifier = modifier
                    )
                }
            }
        }
    }
}