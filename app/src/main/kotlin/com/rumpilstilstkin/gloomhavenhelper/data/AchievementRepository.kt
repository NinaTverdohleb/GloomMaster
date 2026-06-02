package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.AchievementDao
import com.rumpilstilstkin.gloomhavenhelper.di.ApplicationScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementRepository @Inject constructor(
    private val achievementDao: AchievementDao,
    localeRepository: LocaleRepository,
    @ApplicationScope externalScope: CoroutineScope,
) {
    // Hot, app-scoped snapshot of the achievement-name dictionary for the active locale.
    // null = not loaded yet; an empty map is a valid loaded state. Recomputed once per locale.
    private val dictionaryState: StateFlow<Map<String, String>?> =
        localeRepository.observeLocale
            .map { locale ->
                achievementDao
                    .getAllTranslations(
                        targetLocale = locale,
                        defaultLocale = LocaleRepository.DEFAULT_LOCALE,
                    ).associate { it.slug to it.name }
            }.stateIn(
                scope = externalScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null,
            )

    /** Reactive dictionary that only emits real (loaded) snapshots, never the initial null. */
    val dictionary: Flow<Map<String, String>> = dictionaryState.filterNotNull()

    /** Suspend read that waits for the first loaded snapshot; won't hang on a valid empty map. */
    suspend fun currentDictionary(): Map<String, String> = dictionary.first()

    suspend fun getGlobalAchievementsByPacks(packs: List<String>): List<Achievement> =
        achievementDao
            .getGlobalAchievementsByPacks(
                packs = packs,
            ).map {
                Achievement(
                    value = 1,
                    maxValue = it.maxRang,
                    slug = it.slug,
                    isGlobal = it.isGlobal,
                )
            }

    suspend fun getTeamAchievementsByPacks(packs: List<String>): List<Achievement> =
        achievementDao
            .getTeamAchievementsByPacks(
                packs,
            ).map {
                Achievement(
                    value = 1,
                    maxValue = it.maxRang,
                    slug = it.slug,
                    isGlobal = it.isGlobal,
                )
            }

    suspend fun getAchievementsNameBySlugs(
        slugs: List<String>,
        locale: String,
    ): Map<String, String> =
        achievementDao
            .getTeamAchievementsBySlugs(
                slugs = slugs,
                defaultLocale = LocaleRepository.DEFAULT_LOCALE,
                targetLocale = locale,
            ).associate { it.slug to it.name }
}
