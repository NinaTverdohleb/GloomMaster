package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.global

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement.AddOrUpdateAchievementUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement.GetAvailableGlobalAchievementsUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamShortInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.core.createOverlaySession
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.AchievementsAction
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.AchievementsStateUi
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.add.AddAchievementDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.delete.DeleteAchievementDialogContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlobalAchievementsViewModel @Inject constructor(
    getCurrentTeamShortInfoUseCase: GetCurrentTeamShortInfoUseCase,
    getAvailableAchievementsUseCase: GetAvailableGlobalAchievementsUseCase,
    private val addOrUpdateAchievementUseCase: AddOrUpdateAchievementUseCase,
) : ViewModel() {
    private val _screenEvents = Channel<ScreenEffect>(Channel.BUFFERED)
    val screenEvents = _screenEvents.receiveAsFlow()

    val uiState: StateFlow<AchievementsStateUi> =
        combine(
            getCurrentTeamShortInfoUseCase(),
            getAvailableAchievementsUseCase(),
        ) { team, availableAchievements ->
            AchievementsStateUi(
                achievements =
                    team
                        ?.achievements
                        ?.filter { it.isGlobal }
                        ?.toImmutableList() ?: persistentListOf(),
                availableAchievements = availableAchievements.toImmutableList(),
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = AchievementsStateUi(),
            started = SharingStarted.WhileSubscribed(5000),
        )

    fun onAction(action: AchievementsAction) {
        viewModelScope.launch {
            when (action) {
                is AchievementsAction.ShowAddDialog -> {
                    showAddAchievementDialog(uiState.value.availableAchievements)
                }

                is AchievementsAction.DeleteAchievement -> {
                    showDeleteAchievementDialog(action.achievement)
                }

                is AchievementsAction.Back -> {
                    _screenEvents.send(ScreenEffect.Navigation(GlHelperEvent.Back))
                }

                is AchievementsAction.UpdateAchievement -> {
                    addOrUpdateAchievementUseCase(
                        action.achievement.copy(value = action.value).toAchievement(),
                    )
                }
            }
        }
    }

    private fun showAddAchievementDialog(available: ImmutableList<AchievementWithName>) {
        viewModelScope.launch {
            val session =
                createOverlaySession(
                    contract = AddAchievementDialogContract,
                    input = available,
                    onResult = { },
                )
            _screenEvents.send(ScreenEffect.OpenBottomSheet(session))
        }
    }

    private fun showDeleteAchievementDialog(achievement: AchievementWithName) {
        viewModelScope.launch {
            val session =
                createOverlaySession(
                    contract = DeleteAchievementDialogContract,
                    input = achievement,
                    onResult = { },
                )
            _screenEvents.send(ScreenEffect.OpenDialog(session))
        }
    }
}
