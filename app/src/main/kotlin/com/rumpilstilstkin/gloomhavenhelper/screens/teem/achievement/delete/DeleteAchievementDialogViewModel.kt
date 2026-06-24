package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement.RemoveAchievementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DeleteAchievementDialogViewModel @Inject constructor(
    private val removeAchievementUseCase: RemoveAchievementUseCase,
) : ViewModel() {
    private val _complete = Channel<DeleteAchievementDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: DeleteAchievementDialogAction) {
        viewModelScope.launch {
            when (action) {
                is DeleteAchievementDialogAction.DeleteAchievement -> {
                    removeAchievementUseCase(action.achievementSlug)
                    _complete.send(DeleteAchievementDialogComplete)
                }
            }
        }
    }
}
