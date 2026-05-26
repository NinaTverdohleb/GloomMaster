package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TranslationDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TranslationBd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Reads the translation store and exposes it as a [TextResolver] for a locale. Not cached: the
 * store is observed, so a (re)seed at startup and a live language switch both flow through to
 * the display.
 */
@Singleton
class TranslationRepository @Inject constructor(
    private val translationDao: TranslationDao,
    private val achievementKeyIndex: AchievementKeyIndex,
) {
    /** One-off snapshot resolver, for suspend (non-stream) reads. */
    suspend fun resolver(locale: String): TextResolver =
        translationDao.getForLocale(locale).toResolver()

    /** Reactive resolver that re-emits when the store changes (e.g. after seeding). */
    fun resolverFlow(locale: String): Flow<TextResolver> =
        translationDao.getForLocaleFlow(locale).map { it.toResolver() }

    private fun List<TranslationBd>.toResolver(): TextResolver =
        TextResolver(
            byKey = associate { TextResolver.key(it.entityType, it.entityKey, it.fieldName) to it.text },
            achievementKeys = achievementKeyIndex.byName,
        )
}
