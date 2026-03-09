package com.rumpilstilstkin.gloomhavenhelper.ui.icons.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
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

val iconsInlineContentMap: Map<String, InlineTextContent> =
    GameIcon.entries.associate { icon ->
        icon.textCode.value to InlineTextContent(
            Placeholder(
                width = 38.sp,
                height = 38.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Image(
                painter = painterResource(id = icon.imageRes),
                contentDescription = "Icon",
                modifier = Modifier
                    .size(58.dp)
                    .padding(2.dp)
            )
        }
    }