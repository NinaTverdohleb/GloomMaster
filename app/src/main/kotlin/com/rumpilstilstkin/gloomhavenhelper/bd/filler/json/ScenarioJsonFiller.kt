package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.ScenarioJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.ScenarioTranslationJson
import javax.inject.Inject

class ScenarioJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val scenarioDao: ScenarioDao
) {
    suspend fun fill(pack: String) {
        val file = "scenarios.json"
        val scenarios = jsonDataLoader.loadDictionaryList<ScenarioJson>(file, pack)
        val entities = scenarios.map { it.toEntity() }
        scenarioDao.insertAll(*entities.toTypedArray())

        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            val translations =
                jsonDataLoader.loadDictionaryList<ScenarioTranslationJson>(file, "$pack/$locale")
            val translationsEntities = translations.map { it.toEntity(locale) }
            scenarioDao.insertAll(*translationsEntities.toTypedArray())
        }
    }
}
