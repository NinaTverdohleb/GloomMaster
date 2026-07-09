package com.rumpilstilstkin.gloommaster.bd.filler.json

import com.rumpilstilstkin.gloommaster.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloommaster.bd.filler.json.models.GameLevelJson
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
