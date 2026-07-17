package com.rumpilstilstkin.gloommaster.data

import com.rumpilstilstkin.gloommaster.bd.dao.CharacterPersonalQuestDao
import com.rumpilstilstkin.gloommaster.bd.dao.PersonalQuestDao
import com.rumpilstilstkin.gloommaster.bd.entity.CharacterPersonalQuestBd
import com.rumpilstilstkin.gloommaster.bd.entity.PersonalQuestBd
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.domain.entity.quest.CharacterPersonalQuest
import com.rumpilstilstkin.gloommaster.domain.entity.quest.CharacterPersonalQuestShort
import com.rumpilstilstkin.gloommaster.domain.entity.quest.CharacterTaskItem
import com.rumpilstilstkin.gloommaster.domain.entity.quest.QuestReward
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.map

@Singleton
class QuestsRepository @Inject constructor(
    private val personalQuestDao: PersonalQuestDao,
    private val characterPersonalQuestDao: CharacterPersonalQuestDao,
) {
    suspend fun getQuests(locale: String) =
        personalQuestDao.getQuests().map { quest ->
            quest.toDomain(locale)
        }

    fun getCharacterPersonalQuestFlow(
        characterId: Int,
        locale: String,
    ) = characterPersonalQuestDao.getCharacterPersonalQuestFlow(characterId).map { quest ->
        quest?.personalTask?.toDomain(
            locale,
            quest.characterPersonalQuest,
        )
    }

    suspend fun getQuestById(questId: String) =
        CharacterPersonalQuestShort(
            questId = questId,
            tasks = personalQuestDao.getQuest(questId).tasks,
        )

    suspend fun getCharacterQuestById(characterId: Int): CharacterPersonalQuestShort? =
        characterPersonalQuestDao.getCharacterQuestById(characterId)?.let { quest ->
            CharacterPersonalQuestShort(
                questId = quest.personalTask.questId,
                tasks = quest.characterPersonalQuest.tasks,
            )
        }

    suspend fun setQuestForCharacter(
        quest: CharacterPersonalQuestShort,
        characterId: Int,
    ) {
        characterPersonalQuestDao.insert(
            CharacterPersonalQuestBd(
                characterId = characterId,
                questId = quest.questId,
                tasks = quest.tasks,
            ),
        )
    }

    suspend fun updateCharacterQuest(
        quest: CharacterPersonalQuestShort,
        characterId: Int,
    ) {
        deleteCharacterQuests(characterId)
        setQuestForCharacter(
            quest = quest,
            characterId = characterId,
        )
    }

    suspend fun deleteCharacterQuests(characterId: Int) = characterPersonalQuestDao.deleteByCharacterId(characterId)

    private suspend fun PersonalQuestBd.toDomain(
        locale: String,
        personal: CharacterPersonalQuestBd? = null,
    ): CharacterPersonalQuest {
        val questTranslations =
            personalQuestDao.getQuestTranslation(
                questId = questId,
                targetLocale = locale,
                defaultLocale = LocaleRepository.DEFAULT_LOCALE,
            )
        val taskTranslations =
            personalQuestDao.getTasksTranslation(
                questId = questId,
                targetLocale = locale,
                defaultLocale = LocaleRepository.DEFAULT_LOCALE,
            )
        return CharacterPersonalQuest(
            questId = questId,
            title = questTranslations.title,
            descriptions = questTranslations.description,
            tasks =
                (personal?.tasks ?: tasks).map { task ->
                    val tr = taskTranslations.firstOrNull { it.taskId == task.id }
                    if (tr != null) {
                        when (task) {
                            is CharacterTaskItem.Check -> task.copy(text = tr.text)
                            is CharacterTaskItem.Count -> task.copy(text = tr.text)
                        }
                    } else {
                        task
                    }
                },
            reward =
                QuestReward(
                    classType = characterType?.let { CharacterClassType.valueOf(it) },
                    alternativeReward = questTranslations.specialText,
                ),
        )
    }
}
