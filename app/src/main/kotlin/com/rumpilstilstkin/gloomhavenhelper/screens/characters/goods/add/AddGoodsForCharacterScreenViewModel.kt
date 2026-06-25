package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharacterGeneralInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.AddGoodForCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.BuyGoodForCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.GetGoodsForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetDiscountByReputationUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetTeamInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect.*
import com.rumpilstilstkin.gloomhavenhelper.screens.core.createOverlaySession
import com.rumpilstilstkin.gloomhavenhelper.screens.goods.GoodDetailsDialogContract
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
    getGoodsForCurrentTeamUseCase: GetGoodsForCurrentTeamUseCase,
    private val getCharacterGeneralInfoUseCase: GetCharacterGeneralInfoUseCase,
    private val getDiscountByReputationUseCase: GetDiscountByReputationUseCase,
    private val getTeamUseCase: GetTeamInfoUseCase,
) : ViewModel() {
    private val _screenEvents = MutableSharedFlow<ScreenEffect>()
    val screenEvents = _screenEvents.asSharedFlow()

    private val logicState = MutableStateFlow(AddGoodsForCharacterScreenLogicState())

    val uiState: StateFlow<AddGoodsForCharacterScreenUiState> =
        combine(
            logicState,
            getGoodsForCurrentTeamUseCase(),
        ) { state, all ->
            val availableGoods: List<GoodUi> =
                all
                    .filter { it.filterResult(state.selectedFilter, state.searchText) }
                    .map { it.toUi() }
                    .minus(state.selectedGoods.toSet())
                    .sortedBy { it.number }
            val selectedGoods = state.selectedGoods.sortedBy { it.number }.toImmutableList()

            AddGoodsForCharacterScreenUiState(
                goodsState =
                    AddGoodsViewState(
                        availableGoods = availableGoods.toImmutableList(),
                        selectedGoods = selectedGoods,
                        selectedFilter = state.selectedFilter,
                        searchText = state.searchText,
                    ),
                allGold = state.allGold,
                goodsGold = selectedGoods.sumOf { it.cost + state.discount },
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = AddGoodsForCharacterScreenUiState(),
            started = SharingStarted.WhileSubscribed(5000),
        )

    init {
        viewModelScope.launch {
            getCharacterGeneralInfoUseCase(id)?.also { characterInfo ->
                val teamInfo = characterInfo.teamId?.let { getTeamUseCase(characterInfo.teamId) }
                val totalGold = characterInfo.goldCount
                val discount = getDiscountByReputationUseCase(teamInfo?.reputation ?: 0)
                logicState.update {
                    it.copy(
                        allGold = totalGold,
                        discount = discount,
                    )
                }
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
                    addCharacterGoodsUseCase(
                        goodIds = logicState.value.selectedGoods.map { it.goodId },
                        characterId = id,
                    )
                    _screenEvents.emit(Navigation(GlHelperEvent.Back))
                }

                is AddGoodsForCharacterScreenActions.BuySelectedGoods -> {
                    val cost = uiState.value.goodsGold
                    buyGoodForCharacterUseCase(
                        goodIds = logicState.value.selectedGoods.map { it.goodId },
                        cost = cost,
                        characterId = id,
                    ).onSuccess {
                        _screenEvents.emit(Navigation(GlHelperEvent.Back))
                    }
                }

                is AddGoodsForCharacterScreenActions.Close -> {
                    _screenEvents.emit(Navigation(GlHelperEvent.Back))
                }

                is AddGoodsForCharacterScreenActions.SelectFilter -> {
                    val newFilter =
                        if (action.type == logicState.value.selectedFilter) null else action.type
                    logicState.update { it.copy(selectedFilter = newFilter) }
                }

                is AddGoodsForCharacterScreenActions.SearchTextChange -> {
                    logicState.update { it.copy(searchText = action.text) }
                }

                is AddGoodsForCharacterScreenActions.OpenGood -> {
                    val session =
                        createOverlaySession(
                            contract = GoodDetailsDialogContract,
                            input = action.good,
                            onResult = { },
                        )
                    _screenEvents.emit(ScreenEffect.OpenDialog(session))
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): AddGoodsForCharacterScreenViewModel
    }
}
