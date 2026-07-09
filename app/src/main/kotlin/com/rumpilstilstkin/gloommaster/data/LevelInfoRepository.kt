package com.rumpilstilstkin.gloommaster.data

import com.rumpilstilstkin.gloommaster.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloommaster.data.mappers.toDomain
import com.rumpilstilstkin.gloommaster.domain.entity.LevelInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LevelInfoRepository @Inject constructor(
    private val levelInfoDao: GameLevelInfoDao,
) {
    private var levelInfoCache: List<LevelInfo> = emptyList()

    suspend fun getLevelInfo(level: Int): Result<LevelInfo> {
        if (levelInfoCache.isEmpty()) {
            levelInfoDao.getAll().let { list ->
                levelInfoCache = list.map { it.toDomain() }
            }
        }
        return levelInfoCache.find { it.level == level }?.let { Result.success(it) }
            ?: Result.failure(Exception("Level not found"))
    }
}
