package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.LocationsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.LocationJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.LocationTranslationJson
import javax.inject.Inject

class LocationJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val dao: LocationsDao,
) {
    suspend fun fill(pack: String) {
        val data =
            jsonDataLoader.loadDictionaryList<LocationJson>("locations.json", pack)
        val entities = data.map { it.toEntity() }
        dao.insertAll(*entities.toTypedArray())
        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            fillTranslations(pack, locale)
        }
    }

    suspend fun fillTranslations(
        pack: String,
        locale: String,
    ) {
        val translations =
            jsonDataLoader.loadDictionaryListOrEmpty<LocationTranslationJson>(
                "locations.json",
                "$pack/$locale",
            )
        val translationsEntities = translations.map { it.toEntity(locale) }
        dao.insertAll(*translationsEntities.toTypedArray())
    }
}
