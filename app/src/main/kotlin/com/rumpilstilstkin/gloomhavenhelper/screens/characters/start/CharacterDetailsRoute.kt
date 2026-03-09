package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.CharacterGeneralTab
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods.CharacterItemsTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks.CharacterPerksTabRoute

@Composable
fun CharacterDetailsRoute(
    characterId: Int,
    navController: NavHostController
) {
    val viewModel =
        hiltViewModel<CharacterDetailsViewModel, CharacterDetailsViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController
            )
        }
    }

    CharacterDetailsScreen(
        state = uiState,
        back = { viewModel.onAction(CharacterDetailsAction.Back) },
        showDeleteDialog = { viewModel.onAction(CharacterDetailsAction.ShowDeleteDialog) },
        hideDeleteDialog = { viewModel.onAction(CharacterDetailsAction.HideDeleteDialog) },
        confirmDelete = { viewModel.onAction(CharacterDetailsAction.ConfirmDelete) },
        showNameDialog = { viewModel.onAction(CharacterDetailsAction.ShowNameDialog) },
        hideNameDialog = { viewModel.onAction(CharacterDetailsAction.HideNameDialog) },
        saveName = { name -> viewModel.onAction(CharacterDetailsAction.SaveName(name)) },
        showChangeLevelDialog = { viewModel.onAction(CharacterDetailsAction.ShowChangeLevelDialog) },
        hideChangeLevelDialog = { viewModel.onAction(CharacterDetailsAction.HideChangeLevelDialog) },
        changeLevel = { level -> viewModel.onAction(CharacterDetailsAction.ChangeLevel(level)) },
        retire = { viewModel.onAction(CharacterDetailsAction.Retire) },
        selectTab = { tab ->
            when (tab) {
                CharacterDetailsTab.GENERAL -> CharacterGeneralTab(characterId, navController)
                CharacterDetailsTab.STUFF -> CharacterItemsTabRoute(characterId, navController)
                CharacterDetailsTab.SKILLS -> CharacterPerksTabRoute(characterId)
            }
        }
    )

}