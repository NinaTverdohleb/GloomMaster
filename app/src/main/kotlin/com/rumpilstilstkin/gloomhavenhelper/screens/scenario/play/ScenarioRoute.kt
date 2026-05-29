package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.MonsterListDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioActions

@Composable
fun ScenarioRoute(
    navController: NavHostController,
    viewModel: ScenarioViewModel = hiltViewModel()
) {
    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(viewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(viewModel)
        }
    }

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController
            )
        }
    }

    ScenarioScreen(
        state = uiState,
        addMonster = { viewModel.onAction(ScenarioActions.OpenMonstersDialog) },
        back = { navController.popBackStack() },
        complete = { viewModel.onAction(ScenarioActions.CompleteScenario) },
        deleteMonster = { monsterSlug -> viewModel.onAction(ScenarioActions.RemoveMonster(monsterSlug)) },
        deleteUnit = { unitNumber, monsterSlug ->
            viewModel.onAction(
                ScenarioActions.RemoveUnit(
                    number = unitNumber,
                    monsterSlug = monsterSlug
                )
            )
        },
        updateUnitLife = { unitNumber, monsterSlug, life ->
            viewModel.onAction(
                ScenarioActions.UpdateUnitLife(
                    unitNumber = unitNumber,
                    monsterSlug = monsterSlug,
                    newValue = life
                )
            )
        },
        switchUnitEffect = { unitNumber, monsterSlug, effect ->
            viewModel.onAction(
                ScenarioActions.SwitchUnitEffect(
                    unitNumber = unitNumber,
                    monsterSlug = monsterSlug,
                    effect = effect
                )
            )
        },
        addMonsterUnit = { unitNumbers, monsterSlug, isElite ->
            viewModel.onAction(
                ScenarioActions.AddUnits(
                    numbers = unitNumbers,
                    monsterSlug = monsterSlug,
                    isElite = isElite
                )
            )
        },
        nextRound = {
            viewModel.onAction(ScenarioActions.NextRound)
        },
        clickMagic = { magic ->
            viewModel.onAction(ScenarioActions.UpdateMagic((magic)))
        },
        changeUnitLevel = { monsterSlug, unit, level ->
            viewModel.onAction(
                ScenarioActions.UpdateUnitLevel(
                    monsterSlug = monsterSlug,
                    unitNumber = unit.number,
                    level = level,
                    isElite = unit.isSpecial
                )
            )
        }
    )

    if (uiState.showMonsterDialog) {
        MonsterListDialog(
            monsters = uiState.monstersForAdd,
            selectMonster = { viewModel.onAction(ScenarioActions.AddMonster(it)) },
            onDismiss = { viewModel.onAction(ScenarioActions.CloseMonstersDialog) },
            addNewMonsters = { viewModel.onAction(ScenarioActions.AddNewMonsters) }
        )
    }
}