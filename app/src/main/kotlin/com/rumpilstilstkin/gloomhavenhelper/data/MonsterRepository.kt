package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterWithNameBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvailableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStats
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MonsterRepository @Inject constructor(
    val monsterDao: MonsterDao,
    val scenarioDao: ScenarioDao,
) {
    suspend fun getMonsterSlugsForScenario(
        scenarioNumber: Int,
    ): List<String> =
        scenarioDao.getScenario(
            scenarioNumber = scenarioNumber
        ).monsters

    suspend fun getMonsterStats(
        monsterSlug: String,
        level: Int,
        isElite: Boolean,
        locale: String
    ): MonsterStats {
        val stats = monsterDao.getStats(
            monsterSlug = monsterSlug,
            level = level,
            isElite = isElite
        ).let { stats ->
            stats.copy(
                stats = stats.stats + monsterDao.getTextStats(
                    monster = monsterSlug,
                    level = level,
                    isElite = isElite,
                    targetLocale = locale,
                    defaultLocale = LocaleRepository.DEFAULT_LOCALE
                )?.stats.orEmpty()
            )
        }
        return MonsterStats(
            monsterSlug = monsterSlug,
            level = level,
            isElite = isElite,
            life = stats.life,
            stats = stats.stats,
        )
    }

    suspend fun getMonstersForPacks(
        packs: List<String>,
        locale: String
    ): Map<String, String> =
        monsterDao.getMonstersByPacks(
            packs = packs,
            targetLocale = locale,
            defaultLocale = LocaleRepository.DEFAULT_LOCALE
        ).associate { it.monster.slug to it.name }


    suspend fun getMonsterCards(
        slugs: List<String>,
    ): List<AvailableCard> =
        monsterDao.getCardsByMonsterSlugs(slugs).map {
            AvailableCard(
                deck = it.deckName,
                cardId = it.cardId
            )
        }

    suspend fun getMonstersBySlugs(
        slugs: List<String>,
        level: Int,
        locale: String
    ): List<Monster> {
        val distinct = slugs.distinct()
        if (distinct.isEmpty()) return emptyList()
        val monsters: Map<String, MonsterWithNameBd> =
            monsterDao.getMonstersBySlugs(
                slugs = distinct,
                targetLocale = locale,
                defaultLocale = LocaleRepository.DEFAULT_LOCALE
            ).associateBy {
                it.monster.slug
            }
        val stats: Map<Pair<String, Boolean>, MonsterStatsBd> =
            monsterDao.getStatsForMonsters(distinct, level)
                .associateBy { it.monsterSlug to it.isElite }
        val textStats: Map<Pair<String, Boolean>, List<MonsterAction>> =
            monsterDao.getTextStatsForMonsters(
                distinct,
                level,
                locale,
                LocaleRepository.DEFAULT_LOCALE
            ).associateBy { it.monsterSlug to it.isElite }
                .mapValues { it.value.stats }
        val decks = monsters.values.map { it.monster.deckName }.distinct()
        val cardsByDeck = monsterDao.getCardsByDeckNames(decks).groupBy { it.deckName }
        val actionsByDeck =
            monsterDao.getActionCardsByDecks(decks, locale, LocaleRepository.DEFAULT_LOCALE)
                .associateBy { it.deckName to it.cardId }
        return monsters.values.map { monster ->
            val regular = stats[monster.monster.slug to false]
            val elite = stats[monster.monster.slug to true]
            Monster(
                slug = monster.monster.slug,
                name = monster.name,
                life = regular?.life ?: 0,
                stats = regular?.stats.orEmpty() + textStats[monster.monster.slug to false].orEmpty(),
                eliteLife = elite?.life ?: 0,
                eliteStats = elite?.stats.orEmpty()+ textStats[monster.monster.slug to true].orEmpty(),
                cards = cardsByDeck[monster.monster.deckName]?.map { card ->
                    card.toDomain(
                        actionsByDeck[card.deckName to card.cardId]?.actions.orEmpty()
                    )
                }.orEmpty(),
                deckName = monster.monster.deckName,
                isBoss = monster.monster.isBoss,
                immunity = monster.monster.immunity,
                isFly = monster.monster.fly,
                level = level,
                lifeMultiple = monster.monster.lifeMultiple,
            )
        }
    }
}