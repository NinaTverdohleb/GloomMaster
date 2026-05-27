package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.BuildConfig
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TranslationDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TranslationBd
import com.rumpilstilstkin.gloomhavenhelper.localization.SupportedLanguages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Reads the translation store and exposes it as a [TextResolver] for a locale. Not cached: the
 * store is observed, so a (re)seed at startup and a live language switch both flow through to
 * the display.
 *
 * For any locale other than the source, the resolver is also given the source-locale snapshot so
 * release builds can fall back to canonical Russian text on a missing translation (see
 * [TextResolver]); debug builds keep the visible key marker.
 */
@Singleton
class TranslationRepository @Inject constructor(
    private val translationDao: TranslationDao,
    private val achievementKeyIndex: AchievementKeyIndex,
    private val monsterKeyIndex: MonsterKeyIndex,
    private val monsterTextKeyIndex: MonsterTextKeyIndex,
) {
    /** One-off snapshot resolver, for suspend (non-stream) reads. */
    suspend fun resolver(locale: String): TextResolver =
        buildResolver(
            active = translationDao.getForLocale(locale),
            fallback = if (locale == SupportedLanguages.SOURCE_LOCALE) emptyList()
            else translationDao.getForLocale(SupportedLanguages.SOURCE_LOCALE),
        )

    /** Reactive resolver that re-emits when the store changes (e.g. after seeding). */
    fun resolverFlow(locale: String): Flow<TextResolver> =
        if (locale == SupportedLanguages.SOURCE_LOCALE) {
            translationDao.getForLocaleFlow(locale).map { buildResolver(it, emptyList()) }
        } else {
            combine(
                translationDao.getForLocaleFlow(locale),
                translationDao.getForLocaleFlow(SupportedLanguages.SOURCE_LOCALE),
            ) { active, fallback -> buildResolver(active, fallback) }
        }

    private fun buildResolver(active: List<TranslationBd>, fallback: List<TranslationBd>): TextResolver =
        TextResolver(
            byKey = active.toByKey(),
            fallbackByKey = fallback.toByKey(),
            achievementKeys = achievementKeyIndex.byName,
            monsterKeys = monsterKeyIndex.byName,
            monsterTextKeys = monsterTextKeyIndex.byContent,
            markMissingKeys = BuildConfig.DEBUG,
        )

    private fun List<TranslationBd>.toByKey(): Map<String, String> =
        associate { TextResolver.key(it.entityType, it.entityKey, it.fieldName) to it.text }
}
