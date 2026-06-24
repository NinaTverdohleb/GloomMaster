package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.global

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement.GetAvailableGlobalAchievementsUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement.UpdateAchievementUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamShortInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.core.createOverlaySession
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.AchievementsAction
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.AchievementsStateLogic
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.AchievementsStateUi
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.delete.DeleteAchievementDialogContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlobalAchievementsViewModel @Inject constructor(
    getCurrentTeamShortInfoUseCase: GetCurrentTeamShortInfoUseCase,
    getAvailableAchievementsUseCase: GetAvailableGlobalAchievementsUseCase,
    private val updateAchievementUseCase: UpdateAchievementUseCase,
) : ViewModel() {
    private val _screenEvents = MutableSharedFlow<ScreenEffect>()
    val screenEvents = _screenEvents.asSharedFlow()

    private val logicState = MutableStateFlow(AchievementsStateLogic())

    val uiState: StateFlow<AchievementsStateUi> =
        combine(
            getCurrentTeamShortInfoUseCase(),
            getAvailableAchievementsUseCase(),
            logicState,
        ) { team, availableAchievements, logic ->
            AchievementsStateUi(
                achievements =
                    team
                        ?.achievements
                        ?.filter { it.isGlobal }
                        ?.toImmutableList() ?: persistentListOf(),
                availableAchievements = availableAchievements.toImmutableList(),
                showAddDialog = logic.showAddDialog,
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
                    logicState.update { it.copy(showAddDialog = true) }
                }

                is AchievementsAction.DismissAddDialog -> {
                    logicState.update { it.copy(showAddDialog = false) }
                }

                is AchievementsAction.AddAchievement -> {
                    updateAchievementUseCase(action.achievement.toAchievement())
                    logicState.update { it.copy(showAddDialog = false) }
                }

                is AchievementsAction.DeleteAchievement -> {
                    showDeleteAchievementDialog(action.achievement)
                }

                is AchievementsAction.Back -> {
                    _screenEvents.emit(ScreenEffect.Navigation(GlHelperEvent.Back))
                }

                is AchievementsAction.UpdateAchievement -> {
                    updateAchievementUseCase(
                        action.achievement.copy(value = action.value).toAchievement(),
                    )
                }
            }
        }
    }

    private fun showDeleteAchievementDialog(achievement: AchievementWithName) {
        viewModelScope.launch {
            val session = createOverlaySession(
                contract = DeleteAchievementDialogContract,
                input = achievement,
                onResult = { },
            )
            _screenEvents.emit(ScreenEffect.OpenDialog(session))
        }
    }
}
