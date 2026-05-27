package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.data.TextResolver
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement

/**
 * Sets the display-only [Achievement.displayName] for the active locale, resolved from the
 * stable [Achievement.key]. The key is untouched, so saved progress and scenario unlock matching
 * stay language-independent. A missing translation yields the resolver's key marker rather than a
 * blank.
 */
fun Achievement.localized(resolver: TextResolver) = copy(
    displayName = resolver.resolveAchievement(key)
)

fun List<Achievement>.localized(resolver: TextResolver): List<Achievement> =
    map { it.localized(resolver) }
