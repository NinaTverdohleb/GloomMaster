package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods.DeleteCharacterGoodsUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods.GetCharacterGoodsUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods.SellGoodCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterItemsTabViewModel.Factory::class)
class CharacterItemsTabViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getCharacterGoodsUseCase: GetCharacterGoodsUseCase,
    private val deleteCharacterGoodsUseCase: DeleteCharacterGoodsUseCase,
    private val sellGoodCharacterUseCase: SellGoodCharacterUseCase,
) : ViewModel() {

    val uiState: StateFlow<List<GoodUi>> = getCharacterGoodsUseCase(id).map { item ->
        item.map { good -> good.toUi() }.sortedBy { it.number }
    }.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(100),
    )

    fun onAction(action: CharacterItemsTabActions) {
        viewModelScope.launch {
            when (action) {
                is CharacterItemsTabActions.DeleteGood -> {
                    deleteCharacterGoodsUseCase(
                        characterGoodId = action.characterGoodId
                    )
                }

                is CharacterItemsTabActions.SellGood -> {
                    sellGoodCharacterUseCase(characterId = id, characterGoodId = action.characterGoodId)
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CharacterItemsTabViewModel
    }
}

sealed interface CharacterItemsTabActions {
    data class DeleteGood(val characterGoodId: Int) : CharacterItemsTabActions
    data class SellGood(val characterGoodId: Int) : CharacterItemsTabActions
}