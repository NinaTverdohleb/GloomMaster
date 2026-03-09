package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CharacterPerksTabRoute(
    characterId: Int,
) {
    val viewModel =
        hiltViewModel<CharacterPerksTabViewModel, CharacterPerksTabViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CharacterPerkTabScreen(
        uiState = uiState,
        addPerk = {
            viewModel.onAction(CharacterPerksTabActions.OpenAddPerkDialog)
        },
        closeAddPerkDialog = {
            viewModel.onAction(CharacterPerksTabActions.CloseAddPerkDialog)
        },
        deletePerk = {
            viewModel.onAction(CharacterPerksTabActions.DeletePerk(it))
        },
        addPerks = {
            viewModel.onAction(CharacterPerksTabActions.AddPerks(it))
        }
    )

}