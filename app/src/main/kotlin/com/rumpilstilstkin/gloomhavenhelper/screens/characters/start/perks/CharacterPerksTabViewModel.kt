package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks.AddPerksForCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks.DeleteCharacterPerkUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks.GetCharacterPerksInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterPerksTabViewModel.Factory::class)
class CharacterPerksTabViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getCharacterPerksInfoUseCase: GetCharacterPerksInfoUseCase,
    private val deleteCharacterPerksUseCase: DeleteCharacterPerkUseCase,
    private val addCharacterPerksUseCase: AddPerksForCharacterUseCase
) : ViewModel() {

    private val logicState = MutableStateFlow(CharacterPerksScreenStateLogic())

    val uiState: StateFlow<CharacterPerksScreenStateUi> =
        combine(
            logicState,
            getCharacterPerksInfoUseCase(id)
        ){ state, info ->
            CharacterPerksScreenStateUi(
                characterPerks = info.characterPerks.map { perk -> perk.toUi() }.sortedBy { id }
                    .toImmutableList(),
                avaliablePerks = info.avaliablePerks.map { perk -> perk.toUi() }.sortedBy { id }
                    .toImmutableList(),
                avaliablePerksCount = info.avaliablePerksCount,
                showAddPerksDialog = state.showAddPerksDialog,
            )
    }.stateIn(
        scope = viewModelScope,
        initialValue = CharacterPerksScreenStateUi(),
        started = SharingStarted.WhileSubscribed(100),
    )

    fun onAction(action: CharacterPerksTabActions) {
        viewModelScope.launch {
            when (action) {
                is CharacterPerksTabActions.DeletePerk -> {
                    deleteCharacterPerksUseCase(action.characterPerkId)
                }

                is CharacterPerksTabActions.AddPerks -> {
                    logicState.update { it.copy(showAddPerksDialog = false) }
                    addCharacterPerksUseCase(perksIds = action.perks, characterId = id)
                }
                CharacterPerksTabActions.CloseAddPerkDialog -> {
                    logicState.update { it.copy(showAddPerksDialog = false) }
                }
                CharacterPerksTabActions.OpenAddPerkDialog -> {
                    logicState.update { it.copy(showAddPerksDialog = true) }
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CharacterPerksTabViewModel
    }
}

sealed interface CharacterPerksTabActions {
    data class DeletePerk(val characterPerkId: Int) : CharacterPerksTabActions
    data class AddPerks(val perks: List<Int>) : CharacterPerksTabActions
    data object CloseAddPerkDialog : CharacterPerksTabActions
    data object OpenAddPerkDialog  : CharacterPerksTabActions
}
