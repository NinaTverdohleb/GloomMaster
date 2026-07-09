package com.rumpilstilstkin.gloommaster.screens.characters.start.general

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloommaster.domain.entity.quest.CharacterTaskItem
import com.rumpilstilstkin.gloommaster.screens.models.PersonalQuestUI

@Immutable
data class CharacterGeneralTabState(
    val experience: Int,
    val goldCount: Int,
    val hasTeam: Boolean = false,
    val teamName: String? = null,
    val nextLevel: Int,
    val notes: String,
    val checkMarkCount: Int = 0,
    val isDonateAvailable: Boolean = false,
    val personalQuest: PersonalQuestUI? = null,
) {
    companion object {
        val Empty =
            CharacterGeneralTabState(
                experience = 0,
                goldCount = 0,
                nextLevel = 0,
                notes = "",
                personalQuest = null,
            )
    }
}

sealed interface GeneralTabActions {
    data object LevelUp : GeneralTabActions

    data class ExperienceChanged(
        val experience: Int,
    ) : GeneralTabActions

    data class GoldChanged(
        val goldCount: Int,
    ) : GeneralTabActions

    data class CheckedChange(
        val isChecked: Boolean,
    ) : GeneralTabActions

    data object OpenNotes : GeneralTabActions

    data object OpenQuest : GeneralTabActions

    data object ChoosePersonalQuest : GeneralTabActions

    data class TaskCheckedChange(
        val task: CharacterTaskItem.Check,
    ) : GeneralTabActions

    data class TaskCountChanged(
        val task: CharacterTaskItem.Count,
        val count: Int,
    ) : GeneralTabActions
}
