package com.rumpilstilstkin.gloomhavenhelper.screens.characters.general

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem

sealed interface GeneralTabActions {
    data object LevelUp : GeneralTabActions
    data class ExperienceChanged(val experience: Int) : GeneralTabActions
    data class GoldChanged(val goldCount: Int) : GeneralTabActions
    data object Donate : GeneralTabActions
    data class CheckedChange(val isChecked: Boolean) : GeneralTabActions
    data class NotesChanged(val notes: String) : GeneralTabActions
    data object Retire: GeneralTabActions
    data object ChoosePersonalQuest: GeneralTabActions
    data class TaskCheckedChange(val task: CharacterTaskItem.Check) : GeneralTabActions
    data class TaskCountChanged(val task: CharacterTaskItem.Count, val count: Int) : GeneralTabActions
}
