package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.LocationsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.LocationJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.LocationTranslationJson
import javax.inject.Inject

class LocationJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val dao: LocationsDao
) {
    suspend fun fill(pack: String) {
        val file = "locations.json"
        val data =
            jsonDataLoader.loadDictionaryList<LocationJson>(file, pack)
        val entities = data.map { it.toEntity() }
        dao.insertAll(*entities.toTypedArray())
        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            val translations = jsonDataLoader.loadDictionaryList<LocationTranslationJson>(
                file, "$pack/$locale"
            )
            val translationsEntities = translations.map { it.toEntity(locale) }
            dao.insertAll(*translationsEntities.toTypedArray())
        }
    }
}
