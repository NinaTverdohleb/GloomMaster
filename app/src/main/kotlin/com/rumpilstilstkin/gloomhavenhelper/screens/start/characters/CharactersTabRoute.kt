package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character.add.AddCharacterDialog

@Composable
fun CharactersTabRoute(
    navController: NavHostController,
    viewModel: CharactersTabViewModel = hiltViewModel()
) {
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

    CharactersTabScreen(
        uiState = uiState,
        addCharacter = { viewModel.onAction(CharactersTabAction.ShowAddCharacterDialog) },
        openCharacterDetails = { viewModel.onAction(CharactersTabAction.CharacterDetails(it)) },
        switchAlive = { viewModel.onAction(CharactersTabAction.SwitchAlive) }
    )

    if (uiState.showAddCharacterDialog) {
        AddCharacterDialog(
            onDismiss = { viewModel.onAction(CharactersTabAction.CloseAddCharacterDialog) },
            onAdd = { name, level, classType ->
                viewModel.onAction(
                    CharactersTabAction.AddCharacter(
                        name = name,
                        level = level,
                        characterType = classType
                    )
                )
            }
        )
    }
}
