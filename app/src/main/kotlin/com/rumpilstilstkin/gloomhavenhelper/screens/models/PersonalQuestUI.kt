package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterPersonalQuest
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.QuestReward
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class PersonalQuestUI(
    val id: String,
    val title: String,
    val description: String,
    val phases: ImmutableList<QuestTaskPhaseUI>,
    val completed: Boolean = false,
    val reward: QuestReward,
) {
    companion object {
        fun fixture(id: String = "511") =
            PersonalQuestUI(
                id = id,
                title = "Seeker of Xorn",
                description =
                    """
                    Ever since you were a child, you heard the call of Xorn. 
                    Once he was worshipped as a god, but his following has long since been destroyed.
                     But you can hear his call. 
                     You traveled to Gloomhaven by his order.
                      You will find his remains and free him. 
                      What happened once will happen again.
                    """.trimIndent(),
                reward =
                    QuestReward(
                        classType = CharacterClassType.Plagueherald,
                    ),
                phases =
                    persistentListOf(
                        QuestTaskPhaseUI(
                            priority = 0,
                            tasks =
                                persistentListOf(
                                    CharacterTaskItem.Count(
                                        priority = 0,
                                        text = "Complete three scenarios with the name Crypt",
                                        count = 3,
                                        currentCount = 0,
                                        id = 1,
                                    ),
                                ),
                        ),
                        QuestTaskPhaseUI(
                            priority = 1,
                            tasks =
                                persistentListOf(
                                    CharacterTaskItem.Check(
                                        priority = 1,
                                        text = "Open and complete the scenario \"Jekserah's Plans\"",
                                        id = 2,
                                    ),
                                ),
                        ),
                    ),
            )
    }
}

@Immutable
data class QuestTaskPhaseUI(
    val priority: Int,
    val completed: Boolean = false,
    val visible: Boolean = false,
    val tasks: ImmutableList<CharacterTaskItem>,
)

fun CharacterPersonalQuest.toUI() =
    PersonalQuestUI(
        id = this.questId,
        title = this.title,
        description = this.descriptions,
        completed = this.tasks.all { it.completed },
        reward = this.reward,
        phases = tasks.toImmutableListQuestTaskPhaseUIList(),
    )

private fun List<CharacterTaskItem>.toImmutableListQuestTaskPhaseUIList() =
    this
        .groupBy { it.priority }
        .map { (priority, tasks) ->
            QuestTaskPhaseUI(
                priority = priority,
                completed = tasks.all { it.completed },
                tasks = tasks.sortedBy { it.priority }.toImmutableList(),
            )
        }.compile()

private fun List<QuestTaskPhaseUI>.compile() =
    this
        .mapIndexed { index, questTaskPhase ->
            questTaskPhase.copy(visible = questTaskPhase.priority == 0 || this[index - 1].completed)
        }.sortedBy { it.priority }
        .toImmutableList()
