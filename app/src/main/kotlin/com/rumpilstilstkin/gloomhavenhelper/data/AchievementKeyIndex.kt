package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.JsonDataLoader
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Bridges an achievement's canonical (Russian) name to its stable catalog key.
 *
 * The Russian name stays the game-logic and saved-progress identity (scenario unlock matching,
 * persisted team achievements). The translation store, however, is keyed by the stable [key] so
 * dictionaries are not keyed by display strings. This index — loaded once from the canonical
 * catalog asset — is the single source that maps the logic identity to that key, used by
 * [TextResolver.resolveAchievement] when resolving display text.
 */
@Singleton
class AchievementKeyIndex @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
) {
    val byName: Map<String, String> by lazy {
        jsonDataLoader.loadAchievements(CATALOG_VERSION).associate { it.name to it.key }
    }

    private companion object {
        const val CATALOG_VERSION = 1
    }
}
