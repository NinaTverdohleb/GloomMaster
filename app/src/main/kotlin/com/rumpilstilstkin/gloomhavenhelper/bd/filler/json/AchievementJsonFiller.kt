package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.AchievementDao
import javax.inject.Inject

class AchievementJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val achievementDao: AchievementDao
) {
    suspend fun fill(version: Int) {
        val achievements = jsonDataLoader.loadAchievements(version)
        val entities = achievements.map { it.toEntity() }
        achievementDao.insertAll(*entities.toTypedArray())
    }
}
