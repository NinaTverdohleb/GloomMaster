package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.MonsterListDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioActions

@Composable
fun ScenarioRoute(
    navController: NavHostController,
    scenarioId: Int?,
    restore: Boolean,
    viewModel: ScenarioViewModel = hiltViewModel<ScenarioViewModel, ScenarioViewModel.Factory> { factory ->
        factory.create(scenarioId, restore)
    }
) {
    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
        deleteMonster = { monsterId -> viewModel.onAction(ScenarioActions.RemoveMonster(monsterId)) },
        deleteUnit = { unitNumber, monsterId ->
            viewModel.onAction(
                ScenarioActions.RemoveUnit(
                    number = unitNumber,
                    monsterId = monsterId
                )
            )
        },
        updateUnitLife = { unitNumber, monsterId, life ->
            viewModel.onAction(
                ScenarioActions.UpdateUnitLife(
                    unitNumber = unitNumber,
                    monsterId = monsterId,
                    newValue = life
                )
            )
        },
        switchUnitEffect = { unitNumber, monsterId, effect ->
            viewModel.onAction(
                ScenarioActions.SwitchUnitEffect(
                    unitNumber = unitNumber,
                    monsterId = monsterId,
                    effect = effect
                )
            )
        },
        addMonsterUnit = { unitNumbers, monsterId, isElite ->
            viewModel.onAction(
                ScenarioActions.AddUnits(
                    numbers = unitNumbers,
                    monsterId = monsterId,
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
        changeUnitLevel = { monsterId, unit, level ->
            viewModel.onAction(
                ScenarioActions.UpdateUnitLevel(
                    monsterId = monsterId,
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
        )
    }
}