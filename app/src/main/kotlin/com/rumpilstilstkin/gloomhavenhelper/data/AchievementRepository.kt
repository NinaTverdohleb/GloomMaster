package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.AchievementDao
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.localized
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import com.rumpilstilstkin.gloomhavenhelper.localization.LocaleSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementRepository @Inject constructor(
    private val achievementDao: AchievementDao,
    private val translationRepository: TranslationRepository,
    private val localeSource: LocaleSource,
) {
    /**
     * Catalog achievements with display names resolved for the active content locale. A one-off
     * snapshot resolver is used because these are suspend (non-stream) reads; the caller flows
     * re-run on language change (the activity is recreated), so the lists still switch live.
     */
    suspend fun getGlobalAchievements(): List<Achievement> {
        val resolver = translationRepository.resolver(localeSource.current)
        return achievementDao.getGlobalAchievements().map {
            Achievement(key = it.key, value = 1, maxValue = it.maxRang).localized(resolver)
        }
    }

    suspend fun getTeamAchievements(): List<Achievement> {
        val resolver = translationRepository.resolver(localeSource.current)
        return achievementDao.getTeamAchievements().map {
            Achievement(key = it.key, value = 1, maxValue = it.maxRang).localized(resolver)
        }
    }

    suspend fun getGlobalAchievementsByPacks(packs: List<String>): List<Achievement> {
        val resolver = translationRepository.resolver(localeSource.current)
        return achievementDao.getGlobalAchievementsByPacks(packs).map {
            Achievement(key = it.key, value = 1, maxValue = it.maxRang).localized(resolver)
        }
    }

    suspend fun getTeamAchievementsByPacks(packs: List<String>): List<Achievement> {
        val resolver = translationRepository.resolver(localeSource.current)
        return achievementDao.getTeamAchievementsByPacks(packs).map {
            Achievement(key = it.key, value = 1, maxValue = it.maxRang).localized(resolver)
        }
    }
}
