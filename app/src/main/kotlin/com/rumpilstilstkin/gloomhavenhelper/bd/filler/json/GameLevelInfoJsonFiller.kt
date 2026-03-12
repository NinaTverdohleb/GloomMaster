package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import javax.inject.Inject

class GameLevelInfoJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val gameLevelInfoDao: GameLevelInfoDao
) {
    suspend fun fill(version: Int) {
        val levels = jsonDataLoader.loadGameLevels(version)
        val entities = levels.map { it.toEntity() }
        gameLevelInfoDao.insertAll(*entities.toTypedArray())
    }
}
