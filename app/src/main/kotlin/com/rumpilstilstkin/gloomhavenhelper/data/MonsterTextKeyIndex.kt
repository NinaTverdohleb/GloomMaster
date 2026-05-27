package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.JsonDataLoader
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Bridges the canonical (Russian) ability/special text embedded in monster stats to its stable
 * catalog key. The canonical stats data is never modified; at display time the Russian content is
 * matched verbatim against the catalog to find the key, which the per-locale dictionaries are
 * keyed by. Used by [TextResolver.resolveMonsterText].
 */
@Singleton
class MonsterTextKeyIndex @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
) {
    val byContent: Map<String, String> by lazy {
        jsonDataLoader.loadMonsterTexts(CATALOG_VERSION).associate { it.text to it.key }
    }

    private companion object {
        const val CATALOG_VERSION = 1
    }
}
