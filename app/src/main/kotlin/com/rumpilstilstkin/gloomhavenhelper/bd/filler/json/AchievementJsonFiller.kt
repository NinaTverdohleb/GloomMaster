package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.AchievementDao
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.AchievementJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.AchievementTranslationJson
import javax.inject.Inject

class AchievementJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val achievementDao: AchievementDao
) {
    suspend fun fill(pack: String) {
        val file = "achievements.json"
        val data =
            jsonDataLoader.loadDictionaryList<AchievementJson>(file, pack)
        val entities = data.map { it.toEntity() }
        achievementDao.insertAll(*entities.toTypedArray())
        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            val translations = jsonDataLoader.loadDictionaryList<AchievementTranslationJson>(
                file, "$pack/$locale"
            )
            val translationsEntities = translations.map { it.toEntity(locale) }
            achievementDao.insertAll(*translationsEntities.toTypedArray())
        }
    }
}
