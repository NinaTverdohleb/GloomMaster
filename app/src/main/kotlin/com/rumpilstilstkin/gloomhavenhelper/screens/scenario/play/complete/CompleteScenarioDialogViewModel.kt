package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.complete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CompleteScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.CompleteCurrentScenarioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CompleteScenarioDialogViewModel @Inject constructor(
    private val completeCurrentScenarioUseCase: CompleteCurrentScenarioUseCase,
) : ViewModel() {
    private val _complete = Channel<CompleteScenarioDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: CompleteScenarioDialogAction) {
        viewModelScope.launch {
            when (action) {
                CompleteScenarioDialogAction.Complete -> {
                    completeCurrentScenarioUseCase()
                    _complete.send(CompleteScenarioDialogComplete)
                }
            }
        }
    }
}
