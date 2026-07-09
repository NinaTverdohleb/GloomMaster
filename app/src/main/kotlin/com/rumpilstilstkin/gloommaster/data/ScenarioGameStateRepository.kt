package com.rumpilstilstkin.gloommaster.data

import com.rumpilstilstkin.gloommaster.bd.dao.ScenarioGameStateDao
import com.rumpilstilstkin.gloommaster.bd.entity.ScenarioGameStateBd
import com.rumpilstilstkin.gloommaster.domain.entity.ScenarioGameState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScenarioGameStateRepository @Inject constructor(
    private val scenarioGameStateDao: ScenarioGameStateDao,
) {
    suspend fun get(): ScenarioGameState? = scenarioGameStateDao.get()?.toDomain()

    fun getFlow() = scenarioGameStateDao.getFlow()

    suspend fun save(state: ScenarioGameState) {
        scenarioGameStateDao.clearAndInsert(state.toEntity())
    }

    suspend fun delete() {
        scenarioGameStateDao.deleteAll()
    }

    private fun ScenarioGameStateBd.toDomain() =
        ScenarioGameState(
            monsterSlugs = monsterSlugs,
            round = round,
            availableCards = availableCards,
            activeMonsters = activeMonsters,
            magicCharges = magicChargeMap,
            scenarioNumber = scenarioNumber,
            level = level,
        )

    private fun ScenarioGameState.toEntity() =
        ScenarioGameStateBd(
            monsterSlugs = monsterSlugs,
            round = round,
            availableCards = availableCards,
            activeMonsters = activeMonsters,
            magicChargeMap = magicCharges,
            scenarioNumber = scenarioNumber,
            level = level,
        )
}
