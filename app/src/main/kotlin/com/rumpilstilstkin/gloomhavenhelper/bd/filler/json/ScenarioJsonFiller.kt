package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import javax.inject.Inject

class ScenarioJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val scenarioDao: ScenarioDao
) {
    suspend fun fill(version: Int) {
        val scenarios = jsonDataLoader.loadScenarios(version)
        val entities = scenarios.map { it.toEntity() }
        scenarioDao.insertAll(*entities.toTypedArray())
    }

    suspend fun fixScenario21() {
        jsonDataLoader.loadScenarios(1)
            .find { it.scenarioNumber == 21 }
            ?.also { scenarioDao.insertAll(it.toEntity()) }
    }
}
