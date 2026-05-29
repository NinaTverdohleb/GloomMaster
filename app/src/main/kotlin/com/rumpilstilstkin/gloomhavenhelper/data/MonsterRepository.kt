package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStats
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MonsterRepository @Inject constructor(
    val monsterDao: MonsterDao,
    val scenarioDao: ScenarioDao,
) {
    suspend fun getMonsterNamesForScenario(
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
        val monster = monsterDao.getMonsterBySlug(
            monsterSlug,
            targetLocale = locale,
            defaultLocale = LocaleRepository.DEFAULT_LOCALE
        )
        val stats = monsterDao.getStats(
            monster = monster.monster.slug,
            level = level,
            isElite = isElite
        ).let { stats ->
            stats.copy(
                stats = stats.stats + monsterDao.getTextStats(
                    monster = monster.monster.slug,
                    level = level,
                    isElite = isElite,
                    targetLocale = locale,
                    defaultLocale = LocaleRepository.DEFAULT_LOCALE
                )?.stats.orEmpty()
            )
        }
        return MonsterStats(
            monsterSlug = monster.monster.slug,
            level = level,
            isElite = isElite,
            life = stats.life,
            stats = stats.stats,
        )
    }

    suspend fun getMonstersForPacks(packs: List<String>): List<String> =
        monsterDao.getMonstersByPacks(packs).map { monster -> monster.slug }


    suspend fun getMonstersBySlugs(
        slugs: List<String>,
        level: Int,
        locale: String
    ): List<Monster> =
        slugs
            .map { monsterName ->
                val monster = monsterDao.getMonsterBySlug(
                    monsterName,
                    targetLocale = locale,
                    defaultLocale = LocaleRepository.DEFAULT_LOCALE
                )
                val regularStats = monsterDao.getStats(
                    monster = monster.monster.slug,
                    level = level,
                    isElite = false
                ).let { stats ->
                    stats.copy(
                        stats = stats.stats + monsterDao.getTextStats(
                            monster = monster.monster.slug,
                            level = level,
                            isElite = false,
                            targetLocale = locale,
                            defaultLocale = LocaleRepository.DEFAULT_LOCALE
                        )?.stats.orEmpty()
                    )
                }

                val eliteStats = if (monster.monster.isBoss) {
                    null
                } else {
                    monsterDao.getStats(
                        monster = monster.monster.slug,
                        level = level,
                        isElite = true
                    ).let { stats ->
                        stats.copy(
                            stats = stats.stats + monsterDao.getTextStats(
                                monster = monster.monster.slug,
                                level = level,
                                isElite = true,
                                targetLocale = locale,
                                defaultLocale = LocaleRepository.DEFAULT_LOCALE
                            )?.stats.orEmpty()
                        )
                    }
                }

                val cards = monsterDao.getCardsByDeckName(monster.monster.deckName)
                Monster(
                    slug = monster.monster.slug,
                    name = monster.name,
                    life = regularStats.life,
                    stats = regularStats.stats,
                    eliteLife = eliteStats?.life ?: 0,
                    eliteStats = eliteStats?.stats ?: emptyList(),
                    cards = cards.map
                    { it.toDomain() },
                    deckName = monster.monster.deckName,
                    isBoss = monster.monster.isBoss,
                    immunity = monster.monster.immunity,
                    isFly = monster.monster.fly,
                    level = level,
                    lifeMultiple = monster.monster.lifeMultiple,
                )
            }
}