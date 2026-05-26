package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.quests

import com.rumpilstilstkin.gloomhavenhelper.data.QuestsRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.withText
import javax.inject.Inject

class QuestTaskUpdateUseCase @Inject constructor(
    private val questsRepository: QuestsRepository,
) {

    suspend operator fun invoke(
        characterId: Int,
        task: CharacterTaskItem,
    ) {
        questsRepository.getCharacterQuestById(characterId)
            ?.let {
                val newTask = it.copy(
                    tasks = it.tasks.map { existingTask ->
                        if (existingTask.id == task.id) {
                            // Keep the canonical (Russian) stored text; the incoming task carries
                            // the active-language display text, which must not leak into the save.
                            task.withText(existingTask.text)
                        } else {
                            existingTask
                        }
                    }
                )
                newTask
            }
            ?.let { updatedQuest ->
                questsRepository.updateCharacterQuest(updatedQuest, characterId)
            }
    }
}