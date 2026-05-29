package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.global

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement.GetAvailableGlobalAchievementsUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement.RemoveAchievementUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement.UpdateAchievementUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamShortInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.AchievementsAction
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.AchievementsStateLogic
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.AchievementsStateUi
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
    private val removeAchievementUseCase: RemoveAchievementUseCase,
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val logicState = MutableStateFlow(AchievementsStateLogic())

    val uiState: StateFlow<AchievementsStateUi> = combine(
        getCurrentTeamShortInfoUseCase(),
        getAvailableAchievementsUseCase(),
        logicState,
    ) { team, availableAchievements, logic ->
        AchievementsStateUi(
            achievements = team?.achievements?.toImmutableList() ?: persistentListOf(),
            availableAchievements = availableAchievements.toImmutableList(),
            showAddDialog = logic.showAddDialog,
            achievementToDelete = logic.achievementToDelete,
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

                is AchievementsAction.ShowDeleteConfirmDialog -> {
                    logicState.update { it.copy(achievementToDelete = action.achievementSlug) }
                }

                is AchievementsAction.DismissDeleteConfirmDialog -> {
                    logicState.update { it.copy(achievementToDelete = null) }
                }

                is AchievementsAction.ConfirmDelete -> {
                    logicState.value.achievementToDelete?.let { achievement ->
                        removeAchievementUseCase(achievement)
                    }
                    logicState.update { it.copy(achievementToDelete = null) }
                }

                is AchievementsAction.Back -> {
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is AchievementsAction.UpdateAchievement -> {
                    updateAchievementUseCase(
                        action.achievement.copy(value = action.value).toAchievement()
                    )
                }
            }
        }
    }
}
