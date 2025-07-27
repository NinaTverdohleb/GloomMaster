package com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.freeselect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.quests.GetQuestsFlowUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.quests.SetQuestForCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.AddGoodsScreenActions
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PersonalQuestUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = SearchQuestViewModel.Factory::class)
class SearchQuestViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getQuestsFlowUseCase: GetQuestsFlowUseCase,
    private val setQuestForCharacterUseCase: SetQuestForCharacterUseCase
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val quests: StateFlow<List<PersonalQuestUI>> = getQuestsFlowUseCase().map { quests ->
        quests.map { it.toUI() }
    }.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(100),
    )

    private val queryState: MutableStateFlow<String> = MutableStateFlow("")

    internal val uiState: StateFlow<SearchQuestState> = quests.combine(queryState) { quests, query ->
        SearchQuestState(
            quests = quests.filter { it.filterResult(query) }.toImmutableList(),
            searchText = query
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = SearchQuestState(),
        started = SharingStarted.WhileSubscribed(100),
    )

    private fun PersonalQuestUI.filterResult(search: String): Boolean {
        if (search.isBlank()) return true
        if (this.title.contains(search, ignoreCase = true)) return true

        val numberRegex = Regex("\\d+")
        val matchResult = numberRegex.find(search)
        val number = matchResult?.value

        return if (number != null) {
            this.id.contains(number)
        } else false
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): SearchQuestViewModel
    }

    fun onAction(action: SearchQuestActions) {
        viewModelScope.launch {
            when (action) {
                is SearchQuestActions.ChooseQuest -> {
                    setQuestForCharacterUseCase(
                        questId = action.questId,
                        characterId = id
                    )
                    _navigationEvents.emit(GlHelperEvent.Back)
                }
                is SearchQuestActions.Close -> {
                    _navigationEvents.emit(GlHelperEvent.Back)
                }
                is SearchQuestActions.SearchTextChange -> {
                    queryState.emit(action.text)
                }
            }
        }
    }
}

internal data class SearchQuestState(
    val quests: ImmutableList<PersonalQuestUI> = persistentListOf(),
    val searchText: String = "",
)

sealed interface SearchQuestActions {
    data class ChooseQuest(val questId: String) : SearchQuestActions
    data object Close : SearchQuestActions
    data class SearchTextChange(val text: String) : SearchQuestActions
}