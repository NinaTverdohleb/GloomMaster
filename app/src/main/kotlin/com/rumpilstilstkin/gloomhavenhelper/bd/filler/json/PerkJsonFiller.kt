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
        val file = "perks.json"
        val data = jsonDataLoader.loadDictionaryList<CharacterPerksJson>(file, pack)
        val entities = data.map { PerkBd(it.perksCount, it.characterType) }
        perksDao.insertAll(*entities.toTypedArray())

        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            val translationGroups =
                jsonDataLoader.loadDictionaryList<PerkTranslationGroupJson>(file, "$pack/$locale")
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
}
