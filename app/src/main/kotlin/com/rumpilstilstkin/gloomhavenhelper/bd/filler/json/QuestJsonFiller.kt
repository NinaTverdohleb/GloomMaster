package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.PersonalQuestJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.PersonalQuestTranslationJson
import javax.inject.Inject

class QuestJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val personalQuestDao: PersonalQuestDao
) {
    suspend fun fill(pack: String) {
        val quests = jsonDataLoader.loadDictionaryList<PersonalQuestJson>("quests.json", pack)
        val entities = quests.map { it.toEntity() }
        personalQuestDao.insertAll(*entities.toTypedArray())

        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            fillTranslations(pack, locale)
        }
    }

    suspend fun fillTranslations(pack: String, locale: String) {
        val translations =
            jsonDataLoader.loadDictionaryListOrEmpty<PersonalQuestTranslationJson>("quests.json", "$pack/$locale")
        val questTranslations = translations.map { it.toEntity(locale) }
        personalQuestDao.insertTranslations(*questTranslations.toTypedArray())

        val taskTranslations = translations.flatMap { quest ->
            quest.taskTexts.map { task -> task.toEntity(quest.questId, locale) }
        }
        personalQuestDao.insertTaskTranslations(*taskTranslations.toTypedArray())
    }
}
