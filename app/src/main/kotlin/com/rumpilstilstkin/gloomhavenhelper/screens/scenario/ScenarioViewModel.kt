package com.rumpilstilstkin.gloomhavenhelper.screens.scenario

import androidx.lifecycle.ViewModel
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel(assistedFactory = ScenarioViewModel.Factory::class)
class ScenarioViewModel @AssistedInject constructor(
    @Assisted val scenarioId: Int,
): ViewModel() {
    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    @AssistedFactory
    interface Factory {
        fun create(scenarioId: Int): ScenarioViewModel
    }
}

data class ScenarioUIState(
    val name: String,
    val monsters: List<MonsterItem> = emptyList(),
)