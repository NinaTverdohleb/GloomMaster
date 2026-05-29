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
        val scenarios = jsonDataLoader.loadDictionaryList<ScenarioJson>("scenarios.json", pack)
        val entities = scenarios.map { it.toEntity() }
        scenarioDao.insertAll(*entities.toTypedArray())

        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            fillTranslations(pack, locale)
        }
    }

    suspend fun fillTranslations(pack: String, locale: String) {
        val translations =
            jsonDataLoader.loadDictionaryListOrEmpty<ScenarioTranslationJson>("scenarios.json", "$pack/$locale")
        val translationsEntities = translations.map { it.toEntity(locale) }
        scenarioDao.insertAll(*translationsEntities.toTypedArray())
    }
}
