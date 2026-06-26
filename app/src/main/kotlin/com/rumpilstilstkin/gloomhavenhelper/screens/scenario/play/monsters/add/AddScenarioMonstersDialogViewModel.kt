package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterName
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.AddMonstersForCurrentScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.GetAvailableMonstersForTeamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddScenarioMonstersDialogViewModel @Inject constructor(
    private val getAvailableMonstersForTeamUseCase: GetAvailableMonstersForTeamUseCase,
    private val addMonstersForCurrentScenarioUseCase: AddMonstersForCurrentScenarioUseCase,
) : ViewModel() {
    private var allMonsters: List<MonsterName> = emptyList()

    private val _state = MutableStateFlow(AddScenarioMonstersDialogState())
    val state: StateFlow<AddScenarioMonstersDialogState> = _state.asStateFlow()

    private val _complete = Channel<AddScenarioMonstersDialogResult>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    init {
        viewModelScope.launch {
            allMonsters = getAvailableMonstersForTeamUseCase()
            _state.update { it.copy(monsters = allMonsters.toImmutableList()) }
        }
    }

    fun onAction(action: AddScenarioMonstersDialogAction) {
        when (action) {
            is AddScenarioMonstersDialogAction.ChangeSearchText -> {
                _state.update { state ->
                    state.copy(
                        searchText = action.text,
                        monsters = filterMonsters(action.text).toImmutableList(),
                    )
                }
            }

            is AddScenarioMonstersDialogAction.ToggleMonster -> {
                _state.update { state ->
                    val selected =
                        if (state.selectedSlugs.contains(action.slug)) {
                            state.selectedSlugs - action.slug
                        } else {
                            state.selectedSlugs + action.slug
                        }
                    state.copy(selectedSlugs = selected.toImmutableSet())
                }
            }

            AddScenarioMonstersDialogAction.AddMonsters -> {
                val slugs = _state.value.selectedSlugs.toList()
                if (slugs.isEmpty()) return
                viewModelScope.launch {
                    addMonstersForCurrentScenarioUseCase(slugs)
                    _complete.send(AddScenarioMonstersDialogResult(slugs))
                }
            }
        }
    }

    private fun filterMonsters(query: String): List<MonsterName> =
        if (query.isBlank()) {
            allMonsters
        } else {
            allMonsters.filter { it.name.contains(query, ignoreCase = true) }
        }
}
