package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterPersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPersonalQuestBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterPersonalQuest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuestsRepository  @Inject constructor(
    private val personalQuestDao: PersonalQuestDao,
    private val characterPersonalQuestDao: CharacterPersonalQuestDao
) {
    fun getQuestsFlow() = personalQuestDao.getQuestsFlow().map { it.map { it.toDomain() } }

    suspend fun getQuestById(questId: String) = personalQuestDao.getQuest(questId).toDomain()

    suspend fun setQuestForCharacter(quest: CharacterPersonalQuest, characterId: Int) {
        characterPersonalQuestDao.insert(
            CharacterPersonalQuestBd(
                characterId = characterId,
                questId = quest.questId,
                tasks = quest.tasks
            )
        )
    }

    suspend fun deleteCharacterQuests(characterId: Int) = characterPersonalQuestDao.deleteByCharacterId(characterId)
}