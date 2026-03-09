package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharacterGeneralInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods.AddGoodForCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods.BuyGoodForCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods.GetAvailableCharacterGoodsUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.AddGoodsViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel(assistedFactory = AddGoodsForCharacterScreenViewModel.Factory::class)
class AddGoodsForCharacterScreenViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    private val addCharacterGoodsUseCase: AddGoodForCharacterUseCase,
    private val buyGoodForCharacterUseCase: BuyGoodForCharacterUseCase,
    getGodsForCharacterUseCase: GetAvailableCharacterGoodsUseCase,
    private val getCharacterGeneralInfoUseCase: GetCharacterGeneralInfoUseCase,
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val logicState = MutableStateFlow(AddGoodsForCharacterScreenLogicState())

    val uiState: StateFlow<AddGoodsForCharacterScreenUiState> = combine(
        logicState,
        getGodsForCharacterUseCase(id),
    ) { state, all ->
        val availableGoods: List<GoodUi> = all
            .filter { it.filterResult(state.selectedFilter, state.searchText) }
            .map { it.toUi() }
            .minus(state.selectedGoods.toSet())
            .sortedBy { it.number }
        val selectedGoods = state.selectedGoods.sortedBy { it.number }.toImmutableList()

        AddGoodsForCharacterScreenUiState(
            goodsState = AddGoodsViewState(
                availableGoods = availableGoods.toImmutableList(),
                selectedGoods = selectedGoods,
                selectedFilter = state.selectedFilter,
                searchText = state.searchText,
            ),
            allGold = state.allGold,
            goodsGold = selectedGoods.sumOf { it.cost }
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = AddGoodsForCharacterScreenUiState(),
        started = SharingStarted.WhileSubscribed(5000),
    )

    init {
        viewModelScope.launch {
            val totalGold = getCharacterGeneralInfoUseCase(id).goldCount
            logicState.update {
                it.copy(
                    allGold = totalGold
                )
            }
        }
    }

    fun onAction(action: AddGoodsForCharacterScreenActions) {
        viewModelScope.launch {
            when (action) {
                is AddGoodsForCharacterScreenActions.SelectGood -> {
                    logicState.update { state ->
                        state.copy(selectedGoods = state.selectedGoods + action.good)
                    }
                }

                is AddGoodsForCharacterScreenActions.UnselectGood -> {
                    logicState.update { state ->
                        state.copy(selectedGoods = state.selectedGoods - action.good)
                    }
                }

                is AddGoodsForCharacterScreenActions.AddSelectedGoods -> {
                    addCharacterGoodsUseCase(logicState.value.selectedGoods.map { it.id }, id)
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is AddGoodsForCharacterScreenActions.BuySelectedGoods -> {
                    buyGoodForCharacterUseCase(
                        logicState.value.selectedGoods.map { it.number to it.cost },
                        id
                    ).onSuccess {
                        _navigationEvents.emit(GlHelperEvent.Back)
                    }
                }

                is AddGoodsForCharacterScreenActions.Close -> {
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is AddGoodsForCharacterScreenActions.SelectFilter -> {
                    val newFilter =
                        if (action.type == logicState.value.selectedFilter) null else action.type
                    logicState.update { it.copy(selectedFilter = newFilter) }
                }

                is AddGoodsForCharacterScreenActions.SearchTextChange -> {
                    logicState.update { it.copy(searchText = action.text) }
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): AddGoodsForCharacterScreenViewModel
    }
}