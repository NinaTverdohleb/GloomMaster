package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharacterGeneralInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods.AddGoodForCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods.BuyGoodForCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods.GetAvaliableCharacterGoodsUseCase
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = AddGoodsScreenViewModel.Factory::class)
class AddGoodsScreenViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    private val addCharacterGoodsUseCase: AddGoodForCharacterUseCase,
    private val buyGoodForCharacterUseCase: BuyGoodForCharacterUseCase,
    private val getGodsForCharacterUseCase: GetAvaliableCharacterGoodsUseCase,
    private val getCharacterGeneralInfoUseCase: GetCharacterGeneralInfoUseCase,
) : ViewModel() {

    private val logicState: MutableStateFlow<AddGoodsScreenLogicState> = MutableStateFlow(
        AddGoodsScreenLogicState()
    )

    init {
        viewModelScope.launch {
            val availableGoods = getGodsForCharacterUseCase(characterId = id)
            val totalGold = getCharacterGeneralInfoUseCase(id).goldCount
            logicState.emit(
                logicState.value.copy(
                    availableGoods = availableGoods,
                    allGold = totalGold
                )
            )
        }
    }

    internal val uiState: StateFlow<AddGoodsScreenUiState> = logicState.map { state ->
        if (state.availableGoods.isEmpty()){
            AddGoodsScreenUiState()
        } else {
            AddGoodsScreenUiState(
                availableGoods = state.availableGoods
                    .filter {
                        it.filterResult(
                            goodType = state.selectedFilters,
                            search = state.searchText
                        )
                    }.minus(state.selectedGood.toSet())
                    .map { it.toUi() }
                    .sortedBy { it.number }
                    .toImmutableList(),
                selectedGoods = state.selectedGood
                    .map { it.toUi() }
                    .sortedBy { it.number }
                    .toImmutableList(),
                goodsGold = state.selectedGood.sumOf { it.cost },
                selectedFilter = state.selectedFilters,
                searchText = state.searchText,
                isClose = state.isClose,
                allGold = state.allGold
            )
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = AddGoodsScreenUiState(),
        started = SharingStarted.WhileSubscribed(100),
    )

    fun onAction(action: AddGoodsScreenActions) {
        viewModelScope.launch {
            when (action) {
                is AddGoodsScreenActions.SelectGood -> {
                    val good = logicState.value.availableGoods.firstOrNull{it.id == action.id}
                    good?.also { selectedGood ->
                        logicState.emit(
                            logicState.value.copy(
                                selectedGood = logicState.value.selectedGood + selectedGood
                            )
                        )
                    }
                }
                is AddGoodsScreenActions.UnselectGood-> {
                    val good = logicState.value.availableGoods.firstOrNull{it.id == action.id}
                    good?.also { selectedGood ->
                        logicState.emit(
                            logicState.value.copy(
                                selectedGood = logicState.value.selectedGood - selectedGood
                            )
                        )
                    }
                }

                is AddGoodsScreenActions.AddSelectedGoods -> {
                    addCharacterGoodsUseCase.invoke(logicState.value.selectedGood.map { it.id }, id)
                    logicState.emit(logicState.value.copy(isClose = true))
                }

                is AddGoodsScreenActions.BuySelectedGoods -> {
                    buyGoodForCharacterUseCase.invoke(
                        logicState.value.selectedGood,
                        id
                    ).onSuccess {
                        logicState.emit(logicState.value.copy(isClose = true))
                    }
                }

                is AddGoodsScreenActions.Close -> {
                    logicState.emit(logicState.value.copy(isClose = true))
                }

                is AddGoodsScreenActions.SelectFilter -> {
                    val newFilter =
                        if (action.type == logicState.value.selectedFilters) null else action.type
                    logicState.emit(logicState.value.copy(selectedFilters = newFilter))
                }

                is AddGoodsScreenActions.SearchTextChange -> {
                    logicState.emit(logicState.value.copy(searchText = action.text))
                }
            }
        }
    }

    private fun Good.filterResult(goodType: GoodType?, search: String): Boolean {
        if (goodType != null && goodType != this.type) return false
        if (search.isBlank()) return true
        if (this.name.contains(search, ignoreCase = true)) return true

        val numberRegex = Regex("\\d+")
        val matchResult = numberRegex.find(search)
        val number = matchResult?.value?.toIntOrNull()

        return if (number != null) {
            this.number == number
        } else false
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): AddGoodsScreenViewModel
    }
}

internal data class AddGoodsScreenUiState(
    val selectedGoods: ImmutableList<GoodUi> = persistentListOf(),
    val availableGoods: ImmutableList<GoodUi> = persistentListOf(),
    val allGold: Int = 0,
    val goodsGold: Int = 0,
    val selectedFilter: GoodType? = null,
    val searchText: String = "",
    val isClose: Boolean = false,
)

internal data class AddGoodsScreenLogicState(
    val selectedGood: List<Good> = persistentListOf(),
    val availableGoods: List<Good> = persistentListOf(),
    val allGold: Int = 0,
    val selectedFilters: GoodType? = null,
    val searchText: String = "",
    val isClose: Boolean = false,
)

sealed interface AddGoodsScreenActions {
    data class SelectGood(val id: Int) : AddGoodsScreenActions
    data class UnselectGood(val id: Int) : AddGoodsScreenActions
    data object AddSelectedGoods : AddGoodsScreenActions
    data object BuySelectedGoods : AddGoodsScreenActions
    data object Close : AddGoodsScreenActions
    data class SelectFilter(val type: GoodType) : AddGoodsScreenActions
    data class SearchTextChange(val text: String) : AddGoodsScreenActions
}