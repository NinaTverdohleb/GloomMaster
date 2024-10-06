package com.rumpilstilstkin.gloomhavenhelper.screens.characters.perks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks.AddPerksForCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks.DeleteCharacterPerkUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks.GetAvaliableCharacterPerksUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks.GetCharacterPerksUseCase
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


@HiltViewModel(assistedFactory = CharacterPerksTabViewModel.Factory::class)
class CharacterPerksTabViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getCharacterPerksUseCase: GetCharacterPerksUseCase,
    private val getAvaliableCharacterPerksUseCase: GetAvaliableCharacterPerksUseCase,
    private val deleteCharacterPerksUseCase: DeleteCharacterPerkUseCase,
    private val addCharacterPerksUseCase: AddPerksForCharacterUseCase
) : ViewModel() {

    val uiState: StateFlow<CharacterPerksScreenState> = getCharacterPerksUseCase(id).map {
        CharacterPerksScreenState(
            characterPerks = it.sortedBy { id }.map { perk -> perk.toUi() },
            avaliablePerks = getAvaliableCharacterPerksUseCase(id, it).map { perk -> perk.toUi() }
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
