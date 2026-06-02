package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.AchievementJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.GameLevelJson
import javax.inject.Inject

class GameLevelInfoJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val gameLevelInfoDao: GameLevelInfoDao,
) {
    suspend fun fill(pack: String) {
        val levels = jsonDataLoader.loadDictionaryList<GameLevelJson>("game_levels.json", pack)
        val entities = levels.map { it.toEntity() }
        gameLevelInfoDao.insertAll(*entities.toTypedArray())
    }
}
