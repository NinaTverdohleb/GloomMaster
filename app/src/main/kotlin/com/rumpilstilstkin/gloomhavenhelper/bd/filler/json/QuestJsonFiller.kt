package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PersonalQuestBd
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.PersonalQuestJson
import javax.inject.Inject

class QuestJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val personalQuestDao: PersonalQuestDao
) {
    suspend fun fill(version: Int) {
        val quests = jsonDataLoader.loadQuests(version)
        val entities = quests.map { it.toEntity() }
        personalQuestDao.insertAll(*entities.toTypedArray())
    }

    private fun PersonalQuestJson.toEntity() = PersonalQuestBd(
        questId = questId,
        title = title,
        description = description,
        specialText = specialText,
        characterType = characterType,
        tasks = tasks,
        pack = pack
    )
}
