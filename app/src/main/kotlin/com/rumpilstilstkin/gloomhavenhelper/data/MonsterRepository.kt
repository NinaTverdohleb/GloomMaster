package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvaliableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
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

    suspend fun getMonstersForPacks(packs: List<String>): List<String> =
        monsterDao.getMonstersByPacks(packs).map { monster -> monster.slug }


    suspend fun getMonsterCards(
        slugs: List<String>,
    ): List<AvaliableCard> =
        monsterDao.getCardsByMonsterSlugs(slugs).map {
            AvaliableCard(
                deck = it.deckName,
                cardId = it.cardId
            )
        }

    suspend fun getMonstersBySlugs(
        slugs: List<String>,
        level: Int,
        locale: String
    ): List<Monster> =
        slugs
            .map { slug ->
                val monster = monsterDao.getMonsterBySlug(
                    slug = slug,
                    targetLocale = locale,
                    defaultLocale = LocaleRepository.DEFAULT_LOCALE
                )
                val regularStats = getMonsterStats(
                    monsterSlug = slug,
                    level = level,
                    isElite = false,
                    locale = locale
                )

                val eliteStats = if (monster.monster.isBoss) {
                    null
                } else {
                    getMonsterStats(
                        monsterSlug = slug,
                        level = level,
                        isElite = true,
                        locale = locale
                    )
                }

                val cards = monsterDao.getCardsByDeckName(monster.monster.deckName)
                val actions = monsterDao.getActionCards(
                    deck = monster.monster.deckName,
                    targetLocale = locale,
                    defaultLocale = LocaleRepository.DEFAULT_LOCALE
                )
                Monster(
                    slug = monster.monster.slug,
                    name = monster.name,
                    life = regularStats.life,
                    stats = regularStats.stats,
                    eliteLife = eliteStats?.life ?: 0,
                    eliteStats = eliteStats?.stats ?: emptyList(),
                    cards = cards.map { card ->
                        card.toDomain(
                            actions
                                .firstOrNull { actions ->
                                    card.cardId == actions.cardId
                                }
                                ?.actions ?: emptyList()
                        )
                    },
                    deckName = monster.monster.deckName,
                    isBoss = monster.monster.isBoss,
                    immunity = monster.monster.immunity,
                    isFly = monster.monster.fly,
                    level = level,
                    lifeMultiple = monster.monster.lifeMultiple,
                )
            }
}