package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.MonsterJson
import javax.inject.Inject

class MonsterJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val monsterDao: MonsterDao
) {
    suspend fun fillDecks(
        version: Int
    ){
        val decks = jsonDataLoader.loadMonsterDeck(version)
        decks.forEach { deck ->
            val entities = deck.toEntity()
            monsterDao.insertCards(*entities.toTypedArray())
        }
    }

    suspend fun fillMonsters(version: Int) {
        val monsters = jsonDataLoader.loadMonsters(version)
        val entities = monsters.map { it.toEntity() }
        monsterDao.insertMonsters(*entities.toTypedArray())
    }

    // for old versions
    suspend fun fixLivingSpiritDeck(){
        val cards = monsterDao.getCardsByDeckName("living-spirit")
        val newCards = cards.map { card ->
            val initiative = when(card.imageName){
                "ic_deck_ma_ls_1.webp" -> 75
                "ic_deck_ma_ls_2.webp" -> 55
                "ic_deck_ma_ls_3.webp" -> 67
                "ic_deck_ma_ls_4.webp" -> 48
                "ic_deck_ma_ls_5.webp" -> 22
                "ic_deck_ma_ls_6.webp" -> 33
                "ic_deck_ma_ls_7.webp" -> 48
                "ic_deck_ma_ls_8.webp" -> 61
                else -> card.initiative
            }
            card.copy(initiative = initiative)
        }
        monsterDao.insertCards(*newCards.toTypedArray())

    }

    suspend fun fillStats(version: Int, type: String, pack: String) {
        val allMonsters = monsterDao.getAllMonsters()
        val nameToId = allMonsters.associate { it.name to it.monsterId }

        val allStats =
            jsonDataLoader.loadMonsterStats(
                version = version,
                pack = pack,
                type = type
            )

        val entities = allStats.flatMap { monsterStat ->
            val monsterId = nameToId[monsterStat.monsterName]
                ?: throw IllegalStateException("Monster not found: ${monsterStat.monsterName}")
            monsterStat.stats.map { levelStat ->
                MonsterStatsBd(
                    monsterId = monsterId,
                    scenarioLevel = levelStat.level,
                    isElite = levelStat.isElite,
                    life = levelStat.life,
                    stats = levelStat.stats
                )
            }
        }
        monsterDao.insertAllStats(*entities.toTypedArray())
    }
}
