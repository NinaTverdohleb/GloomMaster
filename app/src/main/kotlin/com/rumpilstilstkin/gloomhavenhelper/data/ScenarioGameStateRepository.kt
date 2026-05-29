package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioGameStateDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioGameStateBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScenarioGameStateRepository @Inject constructor(
    private val scenarioGameStateDao: ScenarioGameStateDao,
) {
    suspend fun get(): ScenarioGameState? =
        scenarioGameStateDao.get()?.toDomain()

    fun getFlow() =
        scenarioGameStateDao.getFlow()

    suspend fun save(state: ScenarioGameState) {
        scenarioGameStateDao.insert(state.toEntity())
    }

    suspend fun update(state: ScenarioGameState) {
        scenarioGameStateDao.update(state.toEntity())
    }

    suspend fun delete() {
        scenarioGameStateDao.deleteAll()
    }

    private fun ScenarioGameStateBd.toDomain() = ScenarioGameState(
        monsterNames = monsterNames,
        round = round,
        availableCards = availableCards,
        activeMonsters = activeMonsters,
        magicCharges = magicChargeMap,
        scenarioNumber = scenarioNumber
    )

    private fun ScenarioGameState.toEntity() = ScenarioGameStateBd(
        monsterNames = monsterNames,
        round = round,
        availableCards = availableCards,
        activeMonsters = activeMonsters,
        magicChargeMap = magicCharges,
        scenarioNumber = scenarioNumber
    )
}
