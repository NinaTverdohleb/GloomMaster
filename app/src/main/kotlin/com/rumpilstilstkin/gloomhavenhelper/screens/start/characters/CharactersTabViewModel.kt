package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import androidx.lifecycle.ViewModel
import com.rumpilstilstkin.gloomhavenhelper.data.ClassRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.SaveTeamUsecase
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersTabViewModel@Inject constructor(
    private val classRepository: ClassRepository,
    private val saveTeamUsecase: SaveTeamUsecase
) : ViewModel() {
    
}

sealed interface CharactersTabState {
    data object Empty : CharactersTabState
    data class Data(
        val filters: Filters,
        val characters: List<CharacterUI>,
    ) : CharactersTabState
}

data class Filters(
    val filterAlive: Boolean,
    val filterTeamName: String?,
)