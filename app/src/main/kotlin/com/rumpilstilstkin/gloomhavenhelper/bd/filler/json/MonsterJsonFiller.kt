package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterTextStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.DeckJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.MonsterJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.MonsterStatsJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.MonsterTranslationJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.MonsterTranslationStatsJson
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import javax.inject.Inject

class MonsterJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val monsterDao: MonsterDao
) {
    suspend fun fillDecks(
        pack: String
    ) {
        val decks = jsonDataLoader.loadDictionaryList<DeckJson>("ability_decks.json", pack)
        decks.forEach { deck ->
            val entities = deck.toEntity()
            monsterDao.insertCards(*entities.toTypedArray())
        }
    }

    suspend fun fillMonsters(pack: String) {
        val file = "monsters.json"
        val monsters = jsonDataLoader.loadDictionaryList<MonsterJson>(file, pack)
        val entities = monsters.map { it.toEntity() }
        monsterDao.insertMonsters(*entities.toTypedArray())

        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            val translations =
                jsonDataLoader.loadDictionaryList<MonsterTranslationJson>(file, "$pack/$locale")
            val translationsEntities = translations.map { it.toEntity(locale) }
            monsterDao.insertTranslations(*translationsEntities.toTypedArray())
        }
    }

    suspend fun fillStats(pack: String) {
        fillStats("boss_stats.json", pack)
        fillStats("base_stats.json", pack)
    }

    suspend fun fillStats(fileName: String, pack: String) {
        val translationFile = "text_$fileName"
        val data = jsonDataLoader.loadDictionaryList<MonsterStatsJson>(fileName, pack)

        val entities = data.flatMap { monsterStat ->
            monsterStat.stats.map { levelStat ->
                MonsterStatsBd(
                    monsterSlug = monsterStat.monsterSlug,
                    scenarioLevel = levelStat.level,
                    isElite = levelStat.isElite,
                    life = levelStat.life,
                    stats = levelStat.stats
                )
            }
        }
        monsterDao.insertAllStats(*entities.toTypedArray())

        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            val translations =
                jsonDataLoader.loadDictionaryList<MonsterTranslationStatsJson>(
                    translationFile,
                    "$pack/$locale"
                )
            val translationsEntities = translations.flatMap { monsterStat ->
                monsterStat.stats.map { levelStat ->
                    MonsterTextStatsBd(
                        locale = locale,
                        monsterSlug = monsterStat.monsterSlug,
                        scenarioLevel = levelStat.level,
                        isElite = levelStat.isElite,
                        stats = levelStat.textStats.map {
                            MonsterAction.Text(content = it)
                        }
                    )
                }
            }
            monsterDao.insertTranslations(*translationsEntities.toTypedArray())
        }
    }
}
