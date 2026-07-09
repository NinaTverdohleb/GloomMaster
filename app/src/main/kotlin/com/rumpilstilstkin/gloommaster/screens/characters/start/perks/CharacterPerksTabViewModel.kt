package com.rumpilstilstkin.gloommaster.screens.characters.start.perks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.perks.GetCharacterPerksInfoUseCase
import com.rumpilstilstkin.gloommaster.screens.characters.start.perks.add.AddPerksDialogContract
import com.rumpilstilstkin.gloommaster.screens.characters.start.perks.add.AddPerksDialogInput
import com.rumpilstilstkin.gloommaster.screens.characters.start.perks.delete.DeletePerkDialogContract
import com.rumpilstilstkin.gloommaster.screens.characters.start.perks.delete.DeletePerkDialogInput
import com.rumpilstilstkin.gloommaster.screens.core.ScreenEffect
import com.rumpilstilstkin.gloommaster.screens.core.createOverlaySession
import com.rumpilstilstkin.gloommaster.screens.models.PerkUI
import com.rumpilstilstkin.gloommaster.screens.models.toUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterPerksTabViewModel.Factory::class)
class CharacterPerksTabViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getCharacterPerksInfoUseCase: GetCharacterPerksInfoUseCase,
) : ViewModel() {
    private val _screenEvents = Channel<ScreenEffect>(Channel.BUFFERED)
    val screenEvents = _screenEvents.receiveAsFlow()

    val uiState: StateFlow<CharacterPerksScreenStateUi> =
        getCharacterPerksInfoUseCase(id)
            .map { info ->
                CharacterPerksScreenStateUi(
                    characterPerks =
                        info.characterPerks
                            .map { perk -> perk.toUi() }
                            .sortedBy { it.id }
                            .toImmutableList(),
                    avaliablePerks =
                        info.avaliablePerks
                            .map { perk -> perk.toUi() }
                            .sortedBy { it.id }
                            .toImmutableList(),
                    avaliablePerksCount = info.avaliablePerksCount,
                )
            }.stateIn(
                scope = viewModelScope,
                initialValue = CharacterPerksScreenStateUi(),
                started = SharingStarted.WhileSubscribed(5000),
            )

    fun onAction(action: CharacterPerksTabActions) {
        viewModelScope.launch {
            when (action) {
                is CharacterPerksTabActions.DeletePerk -> {
                    showDeletePerkDialog(action.perk)
                }

                is CharacterPerksTabActions.AddPerk -> {
                    showAddPerksDialog(uiState.value.avaliablePerks)
                }
            }
        }
    }

    private suspend fun showAddPerksDialog(availablePerks: ImmutableList<PerkUI>) {
        val session =
            createOverlaySession(
                contract = AddPerksDialogContract,
                input =
                    AddPerksDialogInput(
                        availablePerks = availablePerks,
                        characterId = id,
                    ),
                onResult = { },
            )
        _screenEvents.send(ScreenEffect.OpenBottomSheet(session))
    }

    private suspend fun showDeletePerkDialog(perk: PerkUI) {
        val session =
            createOverlaySession(
                contract = DeletePerkDialogContract,
                input =
                    DeletePerkDialogInput(
                        perkId = perk.id,
                        characterId = id,
                    ),
                onResult = { },
            )
        _screenEvents.send(ScreenEffect.OpenDialog(session))
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CharacterPerksTabViewModel
    }
}

sealed interface CharacterPerksTabActions {
    data class DeletePerk(
        val perk: PerkUI,
    ) : CharacterPerksTabActions

    data object AddPerk : CharacterPerksTabActions
}
