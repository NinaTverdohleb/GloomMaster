package com.rumpilstilstkin.gloommaster.bd.filler.json

import com.rumpilstilstkin.gloommaster.bd.dao.MonsterDao
import com.rumpilstilstkin.gloommaster.bd.entity.MonsterStatsBd
import com.rumpilstilstkin.gloommaster.bd.entity.MonsterTextStatsBd
import com.rumpilstilstkin.gloommaster.bd.filler.json.models.DeckJson
import com.rumpilstilstkin.gloommaster.bd.filler.json.models.DeckTranslationJson
import com.rumpilstilstkin.gloommaster.bd.filler.json.models.MonsterJson
import com.rumpilstilstkin.gloommaster.bd.filler.json.models.MonsterStatsJson
import com.rumpilstilstkin.gloommaster.bd.filler.json.models.MonsterTranslationJson
import com.rumpilstilstkin.gloommaster.bd.filler.json.models.MonsterTranslationStatsJson
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterAction
import javax.inject.Inject

class MonsterJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val monsterDao: MonsterDao,
) {
    suspend fun fill(pack: String) {
        fillDecks(pack)
        fillMonsters(pack)
        fillStats(pack)
    }


    suspend fun fillDecks(pack: String) {
        val decks = jsonDataLoader.loadDictionaryList<DeckJson>("ability_decks.json", pack)
        decks.forEach { deck ->
            val entities = deck.toEntity()
            monsterDao.insertCards(*entities.toTypedArray())
        }
        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            fillDeckTranslations(pack, locale)
        }
    }

    private suspend fun fillMonsters(pack: String) {
        val file = "monsters.json"
        val monsters = jsonDataLoader.loadDictionaryList<MonsterJson>(file, pack)
        val entities = monsters.map { it.toEntity() }
        monsterDao.insertMonsters(*entities.toTypedArray())

        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            fillMonsterTranslations(pack, locale)
        }
    }

    private suspend fun fillStats(pack: String) {
        fillStats("boss_stats.json", pack)
        fillStats("base_stats.json", pack)
    }

    private suspend fun fillStats(
        fileName: String,
        pack: String,
    ) {
        val data = jsonDataLoader.loadDictionaryList<MonsterStatsJson>(fileName, pack)

        val entities =
            data.flatMap { monsterStat ->
                monsterStat.stats.map { levelStat ->
                    MonsterStatsBd(
                        monsterSlug = monsterStat.monsterSlug,
                        scenarioLevel = levelStat.level,
                        isElite = levelStat.isElite,
                        life = levelStat.life,
                        stats = levelStat.stats,
                    )
                }
            }
        monsterDao.insertAllStats(*entities.toTypedArray())

        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            fillStatsTranslations(fileName, pack, locale)
        }
    }

    /** Loads only the per-locale text (names + stat text) for an additional language. */
    suspend fun fillTranslations(
        pack: String,
        locale: String,
    ) {
        fillMonsterTranslations(pack, locale)
        fillDeckTranslations(pack, locale)
        fillStatsTranslations("boss_stats.json", pack, locale)
        fillStatsTranslations("base_stats.json", pack, locale)
    }

    private suspend fun fillDeckTranslations(
        pack: String,
        locale: String,
    ) {
        val decks =
            jsonDataLoader.loadDictionaryListOrEmpty<DeckTranslationJson>("ability_decks.json", "$pack/$locale")
        decks.forEach { deck ->
            val entities = deck.toEntity(locale)
            monsterDao.insertCardTranslations(*entities.toTypedArray())
        }
    }

    private suspend fun fillMonsterTranslations(
        pack: String,
        locale: String,
    ) {
        val translations =
            jsonDataLoader.loadDictionaryListOrEmpty<MonsterTranslationJson>("monsters.json", "$pack/$locale")
        val translationsEntities = translations.map { it.toEntity(locale) }
        monsterDao.insertTranslations(*translationsEntities.toTypedArray())
    }

    private suspend fun fillStatsTranslations(
        fileName: String,
        pack: String,
        locale: String,
    ) {
        val translationFile = "text_$fileName"
        val translations =
            jsonDataLoader.loadDictionaryListOrEmpty<MonsterTranslationStatsJson>(
                translationFile,
                "$pack/$locale",
            )
        val translationsEntities =
            translations.flatMap { monsterStat ->
                monsterStat.stats.map { levelStat ->
                    MonsterTextStatsBd(
                        locale = locale,
                        monsterSlug = monsterStat.monsterSlug,
                        scenarioLevel = levelStat.level,
                        isElite = levelStat.isElite,
                        stats =
                            levelStat.textStats.map {
                                MonsterAction.Text(content = it)
                            },
                    )
                }
            }
        monsterDao.insertTranslations(*translationsEntities.toTypedArray())
    }
}
