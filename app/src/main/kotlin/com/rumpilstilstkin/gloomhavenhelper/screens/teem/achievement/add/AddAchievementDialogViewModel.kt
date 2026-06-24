package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement.AddOrUpdateAchievementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AddAchievementDialogViewModel @Inject constructor(
    private val addOrUpdateAchievementUseCase: AddOrUpdateAchievementUseCase,
) : ViewModel() {
    private val _complete = Channel<AddAchievementDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: AddAchievementDialogAction) {
        viewModelScope.launch {
            when (action) {
                is AddAchievementDialogAction.AddAchievement -> {
                    addOrUpdateAchievementUseCase(action.achievement.toAchievement())
                    _complete.send(AddAchievementDialogComplete)
                }
            }
        }
    }
}
