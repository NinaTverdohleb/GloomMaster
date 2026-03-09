package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.AchievementDao
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementRepository @Inject constructor(
    private val achievementDao: AchievementDao,
) {
    suspend fun getGlobalAchievements(): List<Achievement> =
        achievementDao.getGlobalAchievements().map {
            Achievement(
                name = it.name,
                value = 1,
                maxValue = it.maxRang
            )
        }

    suspend fun getTeamAchievements(): List<Achievement> =
        achievementDao.getTeamAchievements().map {
            Achievement(
                name = it.name,
                value = 1,
                maxValue = it.maxRang
            )
        }

    suspend fun getGlobalAchievementsByPacks(packs: List<String>): List<Achievement> =
        achievementDao.getGlobalAchievementsByPacks(packs).map {
            Achievement(
                name = it.name,
                value = 1,
                maxValue = it.maxRang
            )
        }

    suspend fun getTeamAchievementsByPacks(packs: List<String>): List<Achievement> =
        achievementDao.getTeamAchievementsByPacks(packs).map {
            Achievement(
                name = it.name,
                value = 1,
                maxValue = it.maxRang
            )
        }
}
