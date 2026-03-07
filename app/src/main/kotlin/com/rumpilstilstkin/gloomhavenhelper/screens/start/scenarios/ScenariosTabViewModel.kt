package com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios

import androidx.lifecycle.ViewModel
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ScenariosTabViewModel @Inject constructor() : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val _uiState = MutableStateFlow(ScenariosTabStateUi.fixture())
    val uiState: StateFlow<ScenariosTabStateUi> = _uiState.asStateFlow()

    fun onAction(action: ScenariosTabAction) {
        when (action) {
            else -> {}
        }
    }
}
