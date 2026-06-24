package com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.selectablePacks
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamWithTeamsUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.SwitchPackForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.UpdateDifficultyLevelUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.UpdateNameForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamEditViewModel @Inject constructor(
    getCurrentTeamWithTeamsCountUseCase: GetCurrentTeamWithTeamsUseCase,
    private val switchPackForCurrentTeamUseCase: SwitchPackForCurrentTeamUseCase,
    private val updateNameForCurrentTeamUseCase: UpdateNameForCurrentTeamUseCase,
    private val updateDifficultyLevelUseCase: UpdateDifficultyLevelUseCase,
) : ViewModel() {
    private val _screenEvents = MutableSharedFlow<ScreenEffect>()
    val screenEvents = _screenEvents.asSharedFlow()


    val uiState: StateFlow<TeamEditStateUi> =
        getCurrentTeamWithTeamsCountUseCase()
            .map { (team, teams) ->
                if (team == null) {
                    TeamEditStateUi()
                } else {
                    val currentPacks = team.packs.toSet()

                    TeamEditStateUi(
                        teamName = team.name,
                        difficultyLevel = team.difficultyLevel,
                        availablePacks =
                            selectablePacks
                                .map { packType ->
                                    PackItemUi(
                                        pack = packType,
                                        isEnabled = packType in currentPacks,
                                    )
                                }.toImmutableList(),
                    )
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = TeamEditStateUi(),
                started = SharingStarted.WhileSubscribed(5000),
            )

    fun onAction(action: TeamEditAction) {
        viewModelScope.launch {
            when (action) {
                is TeamEditAction.ChangeTeamName -> {
                    updateNameForCurrentTeamUseCase(action.name)
                }

                is TeamEditAction.TogglePack -> {
                    switchPackForCurrentTeamUseCase(action.pack)
                }

                is TeamEditAction.Back -> {
                    _screenEvents.emit(ScreenEffect.Navigation(GlHelperEvent.Back))
                }

                is TeamEditAction.ChangeDifficultyLevel -> {
                    updateDifficultyLevelUseCase(action.level)
                }
            }
        }
    }
}
