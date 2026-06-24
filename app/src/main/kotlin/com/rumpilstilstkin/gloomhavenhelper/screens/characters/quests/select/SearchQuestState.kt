package com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.select

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PersonalQuestUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class SearchQuestState(
    val quests: ImmutableList<PersonalQuestUI> = persistentListOf(),
    val searchText: String = "",
)

sealed interface SearchQuestActions {
    data class OpenQuest(
        val quest: PersonalQuestUI,
    ) : SearchQuestActions

    data object Close : SearchQuestActions

    data class SearchTextChange(
        val text: String,
    ) : SearchQuestActions
}
