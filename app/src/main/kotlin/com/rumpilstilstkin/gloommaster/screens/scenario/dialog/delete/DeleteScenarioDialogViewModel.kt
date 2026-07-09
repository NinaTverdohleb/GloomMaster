package com.rumpilstilstkin.gloommaster.screens.scenario.dialog.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.DeleteScenarioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DeleteScenarioDialogViewModel @Inject constructor(
    private val deleteScenarioUseCase: DeleteScenarioUseCase,
) : ViewModel() {
    private val _complete = Channel<DeleteScenarioDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: DeleteScenarioDialogAction) {
        viewModelScope.launch {
            when (action) {
                is DeleteScenarioDialogAction.DeleteScenario -> {
                    deleteScenarioUseCase(action.scenarioNumber)
                    _complete.send(DeleteScenarioDialogComplete)
                }
            }
        }
    }
}
