package com.rumpilstilstkin.gloommaster.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloommaster.navigation.GlHelperScreen
import com.rumpilstilstkin.gloommaster.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloommaster.screens.core.ScreenEffect
import com.rumpilstilstkin.gloommaster.screens.core.createOverlaySession
import com.rumpilstilstkin.gloommaster.screens.teem.create.AddTeamDialogContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    getCurrentTeamUseCase: GetCurrentTeamUseCase,
) : ViewModel() {
    private val _screenEvents = Channel<ScreenEffect>(Channel.BUFFERED)
    val screenEvents = _screenEvents.receiveAsFlow()

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
                    _screenEvents.send(
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
                                _screenEvents.send(ScreenEffect.Message(message))
                            }
                        }
                    },
                )
            _screenEvents.send(ScreenEffect.OpenDialog(session))
        }
    }
}
