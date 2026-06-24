package com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.quests.SetQuestForCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class QuestDetailsDialogViewModel @Inject constructor(
    private val setQuestForCharacterUseCase: SetQuestForCharacterUseCase,
) : ViewModel() {
    private val _complete = Channel<QuestDetailsDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: QuestDetailsDialogAction) {
        viewModelScope.launch {
            when (action) {
                is QuestDetailsDialogAction.SelectQuest -> {
                    setQuestForCharacterUseCase(
                        questId = action.questId,
                        characterId = action.characterId,
                    )
                    _complete.send(QuestDetailsDialogComplete)
                }
            }
        }
    }
}
