package com.rumpilstilstkin.gloomhavenhelper.screens.characters.general

sealed interface GeneralTabActions {
    data object LevelUp : GeneralTabActions
    data class ExperienceChanged(val experience: Int) : GeneralTabActions
    data class GoldChanged(val goldCount: Int) : GeneralTabActions
    data object Donate : GeneralTabActions
    data class CheckedChange(val isChecked: Boolean) : GeneralTabActions
    data class NoticeChanged(val notice: String) : GeneralTabActions
    data object Retire: GeneralTabActions
    data object ChoosePersonalQuest: GeneralTabActions
    data class TaskCheckedChange(val index: Int) : GeneralTabActions
    data class TaskCountChanged(val index: Int, val count: Int) : GeneralTabActions
}
