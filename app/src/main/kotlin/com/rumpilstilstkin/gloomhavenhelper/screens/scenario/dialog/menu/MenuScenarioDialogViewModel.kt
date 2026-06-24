package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CompleteScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.RestoreScenarioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MenuScenarioDialogViewModel @Inject constructor(
    private val restoreScenarioUseCase: RestoreScenarioUseCase,
    private val completeScenarioUseCase: CompleteScenarioUseCase,
) : ViewModel() {
    private val _complete = Channel<MenuScenarioDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: MenuScenarioDialogAction) {
        viewModelScope.launch {
            when (action) {
                is MenuScenarioDialogAction.CompleteScenario -> {
                    completeScenarioUseCase(action.scenario.scenarioNumber)
                    _complete.send(
                        MenuScenarioDialogComplete(
                            MenuScenarioResult.ScenarioCompleted(
                                action.scenario,
                            ),
                        ),
                    )
                }

                is MenuScenarioDialogAction.DeleteScenario -> {
                    _complete.send(
                        MenuScenarioDialogComplete(
                            MenuScenarioResult.DeleteScenarioRequest(
                                action.scenario,
                            ),
                        ),
                    )
                }

                is MenuScenarioDialogAction.RestoreScenario -> {
                    restoreScenarioUseCase(action.scenario.scenarioNumber)
                    _complete.send(
                        MenuScenarioDialogComplete(
                            MenuScenarioResult.ScenarioRestored(
                                action.scenario,
                            ),
                        ),
                    )
                }

                is MenuScenarioDialogAction.StartScenario -> {
                    _complete.send(MenuScenarioDialogComplete(MenuScenarioResult.PlayScenario(action.scenario)))
                }
            }
        }
    }
}
