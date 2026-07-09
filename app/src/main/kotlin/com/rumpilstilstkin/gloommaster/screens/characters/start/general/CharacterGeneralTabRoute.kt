package com.rumpilstilstkin.gloommaster.screens.characters.start.general

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloommaster.screens.core.LaunchedScreenEffect

@Composable
fun CharacterGeneralTabRoute(
    characterId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel =
        hiltViewModel<CharacterGeneralTabViewModel, CharacterGeneralTabViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CharacterGeneralTab(
        state = uiState,
        modifier = modifier,
        openNotes = { viewModel.onAction(GeneralTabActions.OpenNotes) },
        onLevelUp = { viewModel.onAction(GeneralTabActions.LevelUp) },
        onGoldChanged = { viewModel.onAction(GeneralTabActions.GoldChanged(it)) },
        onExperienceChanged = { viewModel.onAction(GeneralTabActions.ExperienceChanged(it)) },
        onCheckedChange = { viewModel.onAction(GeneralTabActions.CheckedChange(it)) },
        choosePersonalQuest = { viewModel.onAction(GeneralTabActions.ChoosePersonalQuest) },
        onTaskCheckedChange = { viewModel.onAction(GeneralTabActions.TaskCheckedChange(it)) },
        showQuestDetails = { viewModel.onAction(GeneralTabActions.OpenQuest) },
        onTaskCountChanged = { task, value ->
            viewModel.onAction(
                GeneralTabActions.TaskCountChanged(
                    task,
                    value,
                ),
            )
        },
    )

    LaunchedScreenEffect(
        effects = viewModel.screenEvents,
        navController = navController,
    )
}
