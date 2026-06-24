package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.AddScenarioToTeamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AddScenarioDialogViewModel @Inject constructor(
    private val addScenarioToTeamUseCase: AddScenarioToTeamUseCase,
) : ViewModel() {
    private val _complete = Channel<AddScenarioDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: AddScenarioDialogAction) {
        viewModelScope.launch {
            when (action) {
                is AddScenarioDialogAction.AddScenario -> {
                    addScenarioToTeamUseCase(action.scenario.scenarioNumber)
                    _complete.send(AddScenarioDialogComplete)
                }
            }
        }
    }
}
