package com.rumpilstilstkin.gloommaster.screens.characters.start.goods.sell

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.SellGoodCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SellGoodDialogViewModel @Inject constructor(
    private val sellGoodCharacterUseCase: SellGoodCharacterUseCase,
) : ViewModel() {
    private val _complete = Channel<SellGoodDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: SellGoodDialogAction) {
        viewModelScope.launch {
            when (action) {
                is SellGoodDialogAction.SellGood -> {
                    sellGoodCharacterUseCase(
                        goodId = action.goodId,
                        characterId = action.characterId,
                        cost = action.cost,
                    )
                    _complete.send(SellGoodDialogComplete)
                }
            }
        }
    }
}
