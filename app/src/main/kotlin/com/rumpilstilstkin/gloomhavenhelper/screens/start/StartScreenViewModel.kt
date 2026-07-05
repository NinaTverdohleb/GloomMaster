package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.core.createOverlaySession
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.create.AddTeamDialogContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    getCurrentTeamUseCase: GetCurrentTeamUseCase,
) : ViewModel() {
    private val _screenEvents = MutableSharedFlow<ScreenEffect>()
    val screenEvents = _screenEvents.asSharedFlow()

    val uiState: StateFlow<StartScreenState> =
        getCurrentTeamUseCase()
            .map { team ->
                if (team == null) {
                    StartScreenState.Empty
                } else {
                    StartScreenState.Team(
                        name = team.name,
                        id = team.id,
                    )
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = StartScreenState.Loading,
                started = SharingStarted.WhileSubscribed(5000),
            )

    fun onAction(action: StartScreenAction) {
        viewModelScope.launch {
            when (action) {
                StartScreenAction.AddTeam -> {
                    openAddTeamDialog()
                }

                StartScreenAction.Settings -> {
                    _screenEvents.emit(
                        ScreenEffect.Navigation(
                            Screen(
                                GlHelperScreen.Settings,
                            ),
                        ),
                    )
                }
            }
        }
    }

    private fun openAddTeamDialog() {
        viewModelScope.launch {
            val session =
                createOverlaySession(
                    contract = AddTeamDialogContract,
                    input = Unit,
                    onResult = { result ->
                        viewModelScope.launch {
                            result?.let { success ->
                                val message =
                                    if (success) {
                                        R.string.team_created_toast
                                    } else {
                                        R.string.error_toast
                                    }
                                _screenEvents.emit(ScreenEffect.Message(message))
                            }
                        }
                    },
                )
            _screenEvents.emit(ScreenEffect.OpenDialog(session))
        }
    }
}
