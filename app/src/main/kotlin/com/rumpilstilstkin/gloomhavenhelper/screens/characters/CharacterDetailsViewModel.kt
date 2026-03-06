package com.rumpilstilstkin.gloomhavenhelper.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharacterGeneralInfoFlowUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.SetTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
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

@HiltViewModel(assistedFactory = CharacterDetailsViewModel.Factory::class)
class CharacterDetailsViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getCharacterUseCase: GetCharacterGeneralInfoFlowUseCase,
    private val setTeamUseCase: SetTeamUseCase
) : ViewModel() {

    val uiState: StateFlow<CharacterUI> = getCharacterUseCase(id).map {
        it.toUi()
    }.stateIn(
        scope = viewModelScope,
        initialValue = CharacterUI(
            name = "",
            level = 0,
            characterClass = CharacterClassUI(
                name = "",
                classType = CharacterClassType.Brute,
            ),
            id = id,
            isAlive = false,
            teamName = null
        ),
        started = SharingStarted.WhileSubscribed(100),
    )

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CharacterDetailsViewModel
    }

    fun onAction(action: CharacterDetailsAction){
        viewModelScope.launch {
            when(action){
                is CharacterDetailsAction.ChangeTeam -> {
                    setTeamUseCase(id, action.teamId)
                }
            }
        }
    }
}

sealed interface CharacterDetailsAction {
    data class ChangeTeam(val teamId: Int) : CharacterDetailsAction
}