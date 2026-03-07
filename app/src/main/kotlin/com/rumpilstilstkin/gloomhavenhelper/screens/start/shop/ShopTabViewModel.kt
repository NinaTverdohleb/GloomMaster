package com.rumpilstilstkin.gloomhavenhelper.screens.start.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.GetGoodsForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.RemoveGoodFromTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens.CharacterDetails
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
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
class ShopTabViewModel @Inject constructor(
    getGoodsForCurrentTeamUseCase: GetGoodsForCurrentTeamUseCase,
    private val removeGoodFromTeamUseCase: RemoveGoodFromTeamUseCase
) : ViewModel() {
    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val logicState = MutableStateFlow(ShopTabStateLogic())
    val uiState: StateFlow<ShopTabStateUi> = combine(
        getGoodsForCurrentTeamUseCase(),
        logicState
    ) { goods, logicState ->
        ShopTabStateUi(
            avaliableGoods = goods
                .filter {
                    it.filterResult(
                        goodType = logicState.selectedFilter,
                        search = logicState.searchText
                    )
                }
                .sortedBy { it.number }
                .map { it.toUi() }
                .toImmutableList(),
            selectedFilter = logicState.selectedFilter,
            searchText = logicState.searchText,
            canAdd = logicState.canAdd
        )

    }.stateIn(
        scope = viewModelScope,
        initialValue = ShopTabStateUi(),
        started = SharingStarted.WhileSubscribed(5000),
    )

    fun onAction(action: ShopTabAction) {
        when (action) {
            is ShopTabAction.AddGood -> viewModelScope.launch {
                _navigationEvents.emit(Screen(GlHelperScreens.AddGoodsForTeam))
            }
            is ShopTabAction.RemoveGood -> viewModelScope.launch { removeGoodFromTeamUseCase(action.number) }
            is ShopTabAction.SearchTextChange -> logicState.update { it.copy(searchText = action.text) }
            is ShopTabAction.SelectFilter -> logicState.update {
                val newFilter = if (it.selectedFilter == action.type) {
                    null
                } else {
                    action.type
                }
                it.copy(selectedFilter = newFilter)
            }
        }
    }
}
