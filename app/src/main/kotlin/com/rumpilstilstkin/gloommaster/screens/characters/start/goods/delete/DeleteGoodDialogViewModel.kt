package com.rumpilstilstkin.gloommaster.screens.characters.start.goods.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.DeleteCharacterGoodsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DeleteGoodDialogViewModel @Inject constructor(
    private val deleteCharacterGoodsUseCase: DeleteCharacterGoodsUseCase,
) : ViewModel() {
    private val _complete = Channel<DeleteGoodDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: DeleteGoodDialogAction) {
        viewModelScope.launch {
            when (action) {
                is DeleteGoodDialogAction.DeleteGood -> {
                    deleteCharacterGoodsUseCase(
                        goodId = action.goodId,
                        characterId = action.characterId,
                    )
                    _complete.send(DeleteGoodDialogComplete)
                }
            }
        }
    }
}
