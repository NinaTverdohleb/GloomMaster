package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.JsonDataLoader
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Bridges a monster's canonical (Russian) name to its stable catalog key.
 *
 * The Russian name stays the game-logic and saved-progress identity (scenario monster lists,
 * active-play game state). The translation store, however, is keyed by the stable catalog key so
 * dictionaries are not keyed by display strings. This index — loaded once from the canonical
 * catalog asset — maps the logic identity to that key, used by [TextResolver.resolveMonster].
 */
@Singleton
class MonsterKeyIndex @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
) {
    val byName: Map<String, String> by lazy {
        jsonDataLoader.loadMonsters(CATALOG_VERSION).associate { it.name to it.key }
    }

    private companion object {
        const val CATALOG_VERSION = 1
    }
}
