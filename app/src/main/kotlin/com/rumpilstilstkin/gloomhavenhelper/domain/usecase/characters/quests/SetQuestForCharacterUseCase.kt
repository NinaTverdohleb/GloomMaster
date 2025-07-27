package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.quests

import com.rumpilstilstkin.gloomhavenhelper.data.QuestsRepository
import javax.inject.Inject

class SetQuestForCharacterUseCase@Inject constructor(
    private val questsRepository: QuestsRepository,
) {
    suspend operator fun invoke(questId: String, characterId: Int) {
        val quest = questsRepository.getQuestById(questId)
        questsRepository.updateCharacterQuest(quest, characterId)
    }
}