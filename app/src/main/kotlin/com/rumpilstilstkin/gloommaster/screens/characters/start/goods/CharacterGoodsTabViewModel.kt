package com.rumpilstilstkin.gloommaster.screens.characters.start.goods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.GetCharacterGoodsUseCase
import com.rumpilstilstkin.gloommaster.navigation.GlHelperScreen
import com.rumpilstilstkin.gloommaster.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloommaster.screens.characters.start.goods.delete.DeleteGoodDialogContract
import com.rumpilstilstkin.gloommaster.screens.characters.start.goods.delete.DeleteGoodDialogInput
import com.rumpilstilstkin.gloommaster.screens.characters.start.goods.sell.SellGoodDialogContract
import com.rumpilstilstkin.gloommaster.screens.characters.start.goods.sell.SellGoodDialogInput
import com.rumpilstilstkin.gloommaster.screens.core.ScreenEffect
import com.rumpilstilstkin.gloommaster.screens.core.createOverlaySession
import com.rumpilstilstkin.gloommaster.screens.goods.GoodDetailsDialogContract
import com.rumpilstilstkin.gloommaster.screens.models.GoodUi
import com.rumpilstilstkin.gloommaster.screens.models.toUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterGoodsTabViewModel.Factory::class)
class CharacterGoodsTabViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getCharacterGoodsUseCase: GetCharacterGoodsUseCase,
) : ViewModel() {
    private val _screenEvents = Channel<ScreenEffect>(Channel.BUFFERED)
    val screenEvents = _screenEvents.receiveAsFlow()

    val uiState: StateFlow<ImmutableList<GoodUi>> =
        getCharacterGoodsUseCase(id)
            .map { item ->
                item
                    .map { good -> good.toUi() }
                    .sortedBy { it.number }
                    .toImmutableList()
            }.stateIn(
                scope = viewModelScope,
                initialValue = persistentListOf(),
                started = SharingStarted.WhileSubscribed(5000),
            )

    fun onAction(action: CharacterItemsTabActions) {
        viewModelScope.launch {
            when (action) {
                is CharacterItemsTabActions.DeleteGood -> {
                    val session =
                        createOverlaySession(
                            contract = DeleteGoodDialogContract,
                            input =
                                DeleteGoodDialogInput(
                                    goodId = action.good.goodId,
                                    characterId = id,
                                    name = action.good.name,
                                ),
                            onResult = { },
                        )
                    _screenEvents.send(ScreenEffect.OpenDialog(session))
                }

                is CharacterItemsTabActions.SellGood -> {
                    val session =
                        createOverlaySession(
                            contract = SellGoodDialogContract,
                            input =
                                SellGoodDialogInput(
                                    goodId = action.good.goodId,
                                    characterId = id,
                                    name = action.good.name,
                                    cost = action.good.cost,
                                ),
                            onResult = { },
                        )
                    _screenEvents.send(ScreenEffect.OpenDialog(session))
                }

                is CharacterItemsTabActions.GoodDetails -> {
                    val session =
                        createOverlaySession(
                            contract = GoodDetailsDialogContract,
                            input = action.good,
                            onResult = { },
                        )
                    _screenEvents.send(ScreenEffect.OpenDialog(session))
                }

                CharacterItemsTabActions.AddGood -> {
                    _screenEvents.send(
                        ScreenEffect.Navigation(
                            GlHelperEvent.Screen(
                                GlHelperScreen.AddGoodsForCharacter(
                                    characterId = id,
                                ),
                            ),
                        ),
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CharacterGoodsTabViewModel
    }
}
