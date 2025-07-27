package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.quests

import android.util.Log
import com.rumpilstilstkin.gloomhavenhelper.data.QuestsRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem
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
                            task
                        } else {
                            existingTask
                        }
                    }
                )
                Log.d("QuestTaskUpdateUseCase", "it: $newTask")
                newTask
            }
            ?.let { updatedQuest ->
                questsRepository.updateCharacterQuest(updatedQuest, characterId)
            }
    }
}