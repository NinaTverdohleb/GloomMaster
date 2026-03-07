package com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ScenariosTabViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ScenariosTabStateUi())
    val uiState: StateFlow<ScenariosTabStateUi> = _uiState.asStateFlow()

    fun onAction(action: ScenariosTabAction) {
        when (action) {
            else -> {}
        }
    }
}
