package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character.AddCharacterDialog

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
        state = uiState,
        addCharacter = { viewModel.onAction(CharactersTabAction.ShowAddCharacterDialog) },
        openCharacterDetails = { viewModel.onAction(CharactersTabAction.CharacterDetails(it)) },
        switchAlive = { viewModel.onAction(CharactersTabAction.SwitchAlive) },
        toggleClass = { viewModel.onAction(CharactersTabAction.SwitchClassAvailability(it)) },
        changeLevel = { characterId, level -> viewModel.onAction(CharactersTabAction.ChangeLevel(characterId, level)) }
    )

    if (uiState.showAddCharacterDialog) {
        AddCharacterDialog(
            avaliableClasses = uiState.avaliableClasses,
            onDismiss = { viewModel.onAction(CharactersTabAction.CloseAddCharacterDialog) },
            addCharacter = { name, level, classType ->
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
