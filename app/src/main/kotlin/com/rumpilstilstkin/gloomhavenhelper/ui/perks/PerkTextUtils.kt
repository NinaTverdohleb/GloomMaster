package com.rumpilstilstkin.gloomhavenhelper.ui.perks

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.IconResCode
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.IconVectorCode

fun replacePerkTextWithIcons(text: String): AnnotatedString {
    val pattern =
        Regex("#(\\d+)") // Matches # followed byone or more digits and captures the digits
    val matches = pattern.findAll(text)

    return buildAnnotatedString {
        var currentIndex = 0
        for (match in matches) {
            append(text.substring(currentIndex, match.range.first))
            withStyle(style = SpanStyle()) {
                appendInlineContent(match.groupValues[1]) // Use the captured number as part of the ID
            }
            currentIndex = match.range.last + 1
        }
        append(text.substring(currentIndex))
    }
}

val perkEffectsInlineContentMap: Map<String, InlineTextContent> =
    IconVectorCode.entries.associate { effectType ->
        effectType.id to InlineTextContent(
            Placeholder(
                width = 28.sp,
                height = 28.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            PerkEffectIcon(effectType)
        }
    } + IconResCode.entries.associate { effectType ->
        effectType.id to InlineTextContent(
            Placeholder(
                width = 28.sp,
                height = 28.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            PerkEffectIcon(effectType)
        }
    }