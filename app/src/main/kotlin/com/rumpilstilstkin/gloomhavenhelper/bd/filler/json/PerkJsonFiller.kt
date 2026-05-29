package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PerksDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PerkBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PerkTranslationBd
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.CharacterPerksJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.PerkTranslationGroupJson
import javax.inject.Inject

class PerkJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val perksDao: PerksDao
) {
    suspend fun fill(pack: String) {
        val data = jsonDataLoader.loadDictionaryList<CharacterPerksJson>("perks.json", pack)
        val entities = data.map { PerkBd(it.perksCount, it.characterType) }
        perksDao.insertAll(*entities.toTypedArray())

        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            fillTranslations(pack, locale)
        }
    }

    suspend fun fillTranslations(pack: String, locale: String) {
        val translationGroups =
            jsonDataLoader.loadDictionaryListOrEmpty<PerkTranslationGroupJson>("perks.json", "$pack/$locale")
        translationGroups.forEach { group ->
            val entities = group.perks.map {
                PerkTranslationBd(
                    perkId = it.id,
                    locale = locale,
                    text = it.text,
                    characterType = group.characterType
                )
            }
            perksDao.insertAll(*entities.toTypedArray())
        }
    }
}
