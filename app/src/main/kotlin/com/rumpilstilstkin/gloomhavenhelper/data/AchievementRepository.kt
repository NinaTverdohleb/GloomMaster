package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.AchievementDao
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementRepository @Inject constructor(
    private val achievementDao: AchievementDao,
    localeRepository: LocaleRepository
) {
    val dictionary: Flow<Map<String, String>> =
        localeRepository.observeLocale.map { locale ->
            achievementDao.getAllTranslations(
                targetLocale = locale,
                defaultLocale = LocaleRepository.DEFAULT_LOCALE
            ).associate { it.slug to it.name }
        }

    suspend fun getGlobalAchievementsByPacks(
        packs: List<String>,
    ): List<Achievement> =
        achievementDao.getGlobalAchievementsByPacks(
            packs = packs,
        ).map {
            Achievement(
                value = 1,
                maxValue = it.maxRang,
                slug = it.slug,
                isGlobal = it.isGlobal
            )
        }

    suspend fun getTeamAchievementsByPacks(
        packs: List<String>,
    ): List<Achievement> =
        achievementDao.getTeamAchievementsByPacks(
            packs,
        ).map {
            Achievement(
                value = 1,
                maxValue = it.maxRang,
                slug = it.slug,
                isGlobal = it.isGlobal
            )
        }

    suspend fun getAchievementsNameBySlugs(
        slugs: List<String>,
        locale: String
    ): Map<String, String> = achievementDao.getTeamAchievementsBySlugs(
        slugs = slugs,
        defaultLocale = LocaleRepository.DEFAULT_LOCALE,
        targetLocale = locale
    ).associate { it.slug to it.name }

}
