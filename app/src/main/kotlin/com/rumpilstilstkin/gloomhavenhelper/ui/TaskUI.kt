package com.rumpilstilstkin.gloomhavenhelper.ui

data class TaskUI (
    val number: Int,
    val name: String,
    val description: String,
    val actions: List<TaskActionUI>
)

sealed interface TaskActionUI {
    val id: Int
    val name: String
    val isComplete: Boolean
    val isAvailable: Boolean

    data class ActionCount(
        override val id: Int,
        override val name: String,
        override val isAvailable: Boolean = true,
        val maxCount: Int,
        val currentCount: Int = 0
    ): TaskActionUI {
        override val isComplete: Boolean = maxCount == currentCount
    }

    data class ActionCheck(
        override val id: Int,
        override val name: String,
        override val isAvailable: Boolean = true,
        val isChecked: Boolean = false
    ): TaskActionUI{
        override val isComplete: Boolean = isChecked
    }
}