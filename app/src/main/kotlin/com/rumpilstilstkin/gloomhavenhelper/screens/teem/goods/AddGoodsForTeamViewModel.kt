package com.rumpilstilstkin.gloomhavenhelper.screens.teem.goods

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.AddGoodToTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.GetAdditionalGoodsForTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.GetAllGoodsUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.GetGoodsForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens.Scenario
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.AddGoodsViewState
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
import javax.inject.Inject

@HiltViewModel
class AddGoodsForTeamViewModel @Inject constructor(
    getGoodsUseCase: GetAdditionalGoodsForTeamUseCase,
    private val addGoodToTeamUseCase: AddGoodToTeamUseCase,
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val logicState = MutableStateFlow(AddGoodsForTeamLogicState())

    val uiState: StateFlow<AddGoodsForTeamUiState> = combine(
        logicState,
        getGoodsUseCase(),
    ) { state, all ->
        val availableGoods: List<GoodUi> = all
            .filter { it.filterResult(state.selectedFilter, state.searchText) }
            .map { it.toUi() }
            .minus(state.selectedGoods.toSet())
            .sortedBy { it.number }

        AddGoodsForTeamUiState(
            goodsState = AddGoodsViewState(
                availableGoods = availableGoods.toImmutableList(),
                selectedGoods = state.selectedGoods.sortedBy { it.number }
                    .toImmutableList(),
                selectedFilter = state.selectedFilter,
                searchText = state.searchText,
            ),
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = AddGoodsForTeamUiState(),
        started = SharingStarted.WhileSubscribed(5000),
    )

    fun onAction(action: AddGoodsForTeamAction) {
        viewModelScope.launch {
            when (action) {
                is AddGoodsForTeamAction.SelectGood -> {
                    logicState.update { state ->
                        state.copy(selectedGoods = state.selectedGoods + action.good)
                    }
                }

                is AddGoodsForTeamAction.UnselectGood -> {
                    logicState.update { state ->
                        state.copy(selectedGoods = state.selectedGoods - action.good)
                    }
                }

                is AddGoodsForTeamAction.AddSelectedGoods -> {
                    logicState.value.selectedGoods.forEach { good ->
                        addGoodToTeamUseCase(good.number)
                    }
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is AddGoodsForTeamAction.SelectFilter -> {
                    val newFilter =
                        if (action.type == logicState.value.selectedFilter) null else action.type
                    logicState.update { it.copy(selectedFilter = newFilter) }
                }

                is AddGoodsForTeamAction.SearchTextChange -> {
                    logicState.update { it.copy(searchText = action.text) }
                }

                AddGoodsForTeamAction.Back -> {
                    _navigationEvents.emit(GlHelperEvent.Back)
                }
            }
        }
    }
}