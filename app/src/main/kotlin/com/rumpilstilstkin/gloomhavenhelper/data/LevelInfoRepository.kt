package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.di.ApplicationScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LevelInfoRepository @Inject constructor(
    private val levelInfoDao: GameLevelInfoDao,
    @ApplicationScope private val externalScope: CoroutineScope,
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