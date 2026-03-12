package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.MonsterJson
import javax.inject.Inject

class MonsterJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val monsterDao: MonsterDao
) {
    suspend fun fillMonsters(version: Int) {
        val monsters = jsonDataLoader.loadMonsters(version)
        val entities = monsters.map { it.toEntity() }
        monsterDao.insertMonsters(*entities.toTypedArray())
    }

    private fun MonsterJson.toEntity() = MonsterBd(
        name = name,
        deckName = deckName,
        isBoss = isBoss,
        fly = fly,
        lifeMultiple = lifeMultiple,
        immunity = immunity,
        pack = pack
    )
}
