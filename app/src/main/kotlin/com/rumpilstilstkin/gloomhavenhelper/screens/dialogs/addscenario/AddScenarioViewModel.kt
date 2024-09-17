package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.addscenario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.screens.main.MainScreenUiState
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AddScenarioViewModel @Inject constructor(
    scenarioRepository: ScenarioRepository,
    teamRepository: TeamRepository
): ViewModel() {
    val uiState: StateFlow<AddScenarioUiState> = teamRepository.currentTeamId.map { team ->
        val teamScenarios = scenarioRepository.getAllTeamScenarios(team)
        val avalibleScenarios = scenarioRepository.getAllScenarios().filter { scenario ->
            teamScenarios.none { it.scenarioNumber == scenario.scenarioNumber }
        }.map { it.toUI() }
        AddScenarioUiState(scenarios = avalibleScenarios)
    }.stateIn(
        scope = viewModelScope,
        initialValue = AddScenarioUiState(),
        started = SharingStarted.WhileSubscribed(10),
    )
}

data class AddScenarioUiState(
    val scenarios: List<ShortScenarioUI> = emptyList()
)