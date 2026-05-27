package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.localized
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterChoice
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStats
import com.rumpilstilstkin.gloomhavenhelper.localization.LocaleSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MonsterRepository @Inject constructor(
    val monsterDao: MonsterDao,
    val scenarioDao: ScenarioDao,
    private val translationRepository: TranslationRepository,
    private val localeSource: LocaleSource,
) {
    suspend fun getMonsterNamesForScenario(
        scenarioNumber: Int,
    ): List<String> =
        scenarioDao.getScenario(
            scenarioNumber = scenarioNumber
        ).monsters

    suspend fun getMonsterStats(monsterId: Int, level: Int, isElite: Boolean): MonsterStats {
        val monster = monsterDao.getMonsterById(monsterId)
        val stats = monsterDao.getStats(
            monsterId = monster.monsterId,
            level = level,
            isElite = isElite
        )
        return MonsterStats(
            monsterId = monster.monsterId,
            level = level,
            isElite = isElite,
            life = stats.life,
            stats = stats.stats,
        ).localized(translationRepository.resolver(localeSource.current))
    }

    suspend fun getMonstersForPacks(packs: List<String>): List<String> =
        monsterDao.getMonstersByPacks(packs).map { monster -> monster.key }

    /**
     * Pairs each monster catalog key with its display name for the active locale. The key stays
     * the selection/persistence identity in the scenario constructor.
     */
    suspend fun localizeMonsterNames(keys: List<String>): List<MonsterChoice> {
        val resolver = translationRepository.resolver(localeSource.current)
        return keys.map { MonsterChoice(name = it, displayName = resolver.resolveMonster(it)) }
    }


    suspend fun getMonstersByNames(
        names: List<String>,
        level: Int
    ): List<Monster> {
        val resolver = translationRepository.resolver(localeSource.current)
        return names
            .map { monsterKey ->
                val monster = monsterDao.getMonsterByKey(monsterKey)
                val regularStats = monsterDao.getStats(
                    monsterId = monster.monsterId,
                    level = level,
                    isElite = false
                )
                val eliteStats = if (monster.isBoss) {
                    null
                } else {
                    monsterDao.getStats(
                        monsterId = monster.monsterId,
                        level = level,
                        isElite = true
                    )
                }
                val cards = monsterDao.getCardsByDeckName(monster.deckName)
                Monster(
                    id = monster.monsterId,
                    key = monster.key,
                    life = regularStats.life,
                    stats = regularStats.stats,
                    eliteLife = eliteStats?.life ?: 0,
                    eliteStats = eliteStats?.stats ?: emptyList(),
                    cards = cards.map { it.toDomain() },
                    deckName = monster.deckName,
                    isBoss = monster.isBoss,
                    immunity = monster.immunity,
                    isFly = monster.fly,
                    level = level,
                    lifeMultiple = monster.lifeMultiple,
                ).localized(resolver)
            }
    }
}