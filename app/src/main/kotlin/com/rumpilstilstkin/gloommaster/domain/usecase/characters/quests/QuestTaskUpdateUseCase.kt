package com.rumpilstilstkin.gloommaster.domain.usecase.characters.quests

import com.rumpilstilstkin.gloommaster.data.QuestsRepository
import com.rumpilstilstkin.gloommaster.domain.entity.quest.CharacterTaskItem
import javax.inject.Inject

class QuestTaskUpdateUseCase @Inject constructor(
    private val questsRepository: QuestsRepository,
) {
    suspend operator fun invoke(
        characterId: Int,
        task: CharacterTaskItem,
    ) {
        questsRepository
            .getCharacterQuestById(characterId)
            ?.let {
                val newTask =
                    it.copy(
                        tasks =
                            it.tasks.map { existingTask ->
                                if (existingTask.id == task.id) {
                                    task
                                } else {
                                    existingTask
                                }
                            },
                    )
                newTask
            }?.let { updatedQuest ->
                questsRepository.updateCharacterQuest(updatedQuest, characterId)
            }
    }
}
