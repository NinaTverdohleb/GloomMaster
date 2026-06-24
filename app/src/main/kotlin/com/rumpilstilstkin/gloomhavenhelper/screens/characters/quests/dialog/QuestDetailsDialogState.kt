package com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.dialog

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PersonalQuestUI

@Immutable
data class QuestDetailsDialogInput(
    val quest: PersonalQuestUI,
    val characterId: Int,
    val selected: Boolean,
)

sealed interface QuestDetailsDialogAction {
    data class SelectQuest(
        val questId: String,
        val characterId: Int,
    ) : QuestDetailsDialogAction
}

data object QuestDetailsDialogComplete
