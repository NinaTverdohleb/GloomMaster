package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterPersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPersonalQuestBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.localized
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterPersonalQuest
import com.rumpilstilstkin.gloomhavenhelper.localization.LocaleSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestsRepository @Inject constructor(
    private val personalQuestDao: PersonalQuestDao,
    private val characterPersonalQuestDao: CharacterPersonalQuestDao,
    private val translationRepository: TranslationRepository,
    private val localeSource: LocaleSource,
) {
    fun getQuestsFlow(): Flow<List<CharacterPersonalQuest>> =
        personalQuestDao.getQuestsFlow()
            .combine(resolverFlow()) { quests, resolver ->
                quests.map { it.toDomain().localized(resolver) }
            }

    suspend fun getQuestById(questId: String) = personalQuestDao.getQuest(questId).toDomain()

    suspend fun getCharacterQuestById(characterId: Int): CharacterPersonalQuest? =
        characterPersonalQuestDao.getCharacterQuestById(characterId)?.toDomain()

    suspend fun setQuestForCharacter(quest: CharacterPersonalQuest, characterId: Int) {
        characterPersonalQuestDao.insert(
            CharacterPersonalQuestBd(
                characterId = characterId,
                questId = quest.questId,
                tasks = quest.tasks
            )
        )
    }

    suspend fun updateCharacterQuest(quest: CharacterPersonalQuest, characterId: Int) {
        deleteCharacterQuests(characterId)
        setQuestForCharacter(
            quest = quest,
            characterId = characterId
        )
    }

    suspend fun deleteCharacterQuests(characterId: Int) =
        characterPersonalQuestDao.deleteByCharacterId(characterId)

    /**
     * Active-locale resolver as a stream: switches when the language changes and re-emits when
     * the translation store is (re)seeded, so quest text refreshes without a restart.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun resolverFlow(): Flow<TextResolver> =
        localeSource.locale.flatMapLatest { translationRepository.resolverFlow(it) }
}