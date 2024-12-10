package com.rumpilstilstkin.gloomhavenhelper.screens.characters.perks

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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterPerksTabViewModel.Factory::class)
class CharacterPerksTabViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getCharacterPerksInfoUseCase: GetCharacterPerksInfoUseCase,
    private val deleteCharacterPerksUseCase: DeleteCharacterPerkUseCase,
    private val addCharacterPerksUseCase: AddPerksForCharacterUseCase
) : ViewModel() {

    val uiState: StateFlow<CharacterPerksScreenState> = getCharacterPerksInfoUseCase(id).map { info ->
        CharacterPerksScreenState(
            characterPerks = info.characterPerks.map { perk -> perk.toUi() }.sortedBy { id }.toImmutableList(),
            avaliablePerks = info.avaliablePerks.map { perk -> perk.toUi() }.sortedBy { id }.toImmutableList(),
            avaliablePerksCount = info.avaliablePerksCount
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = CharacterPerksScreenState(),
        started = SharingStarted.WhileSubscribed(100),
    )

    fun onAction(action: CharacterPerksTabActions) {
        viewModelScope.launch {
            when (action) {
                is CharacterPerksTabActions.DeletePerk -> {
                    deleteCharacterPerksUseCase(action.characterPerkId)
                }

                is CharacterPerksTabActions.AddPerks -> {
                    addCharacterPerksUseCase(perksIds = action.perks, characterId = id)
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
}
