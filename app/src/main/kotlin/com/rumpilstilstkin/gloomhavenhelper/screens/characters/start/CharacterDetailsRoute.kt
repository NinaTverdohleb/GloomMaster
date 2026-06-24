package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.CharacterGeneralTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods.CharacterItemsTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks.CharacterPerksTabRoute

@Composable
fun CharacterDetailsRoute(
    characterId: Int,
    navController: NavHostController,
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
                navController = navController,
            )
        }
    }

    CharacterDetailsScreen(
        state = uiState,
        back = { viewModel.onAction(CharacterDetailsAction.Back) },
        showNameDialog = { viewModel.onAction(CharacterDetailsAction.ShowNameDialog) },
        hideNameDialog = { viewModel.onAction(CharacterDetailsAction.HideNameDialog) },
        saveName = { name -> viewModel.onAction(CharacterDetailsAction.SaveName(name)) },
        selectTab = { tab ->
            when (tab) {
                CharacterDetailsTab.GENERAL -> CharacterGeneralTabRoute(characterId, navController)
                CharacterDetailsTab.STUFF -> CharacterItemsTabRoute(characterId, navController)
                CharacterDetailsTab.SKILLS -> CharacterPerksTabRoute(characterId)
            }
        },
    )
}
