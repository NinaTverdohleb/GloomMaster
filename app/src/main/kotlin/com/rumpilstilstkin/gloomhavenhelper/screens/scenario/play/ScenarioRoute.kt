package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.MonsterListDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.MagicUi
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioActions

@Composable
fun ScenarioRoute(
    navController: NavHostController,
    widthSizeClass: WindowWidthSizeClass,
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
    val addMonster = { viewModel.onAction(ScenarioActions.OpenMonstersDialog) }
    val back: () -> Unit = { navController.popBackStack() }
    val complete = { viewModel.onAction(ScenarioActions.CompleteScenario) }
    val deleteMonster = { monsterSlug: String -> viewModel.onAction(ScenarioActions.RemoveMonster(monsterSlug)) }
    val deleteUnit = { unitNumber: Int, monsterSlug: String ->
        viewModel.onAction(
            ScenarioActions.RemoveUnit(
                number = unitNumber,
                monsterSlug = monsterSlug
            )
        )
    }
    val updateUnitLife = { unitNumber: Int, monsterSlug: String, life: Int ->
        viewModel.onAction(
            ScenarioActions.UpdateUnitLife(
                unitNumber = unitNumber,
                monsterSlug = monsterSlug,
                newValue = life
            )
        )
    }
    val switchUnitEffect = { unitNumber: Int, monsterSlug: String, effect: MonsterStatType ->
        viewModel.onAction(
            ScenarioActions.SwitchUnitEffect(
                unitNumber = unitNumber,
                monsterSlug = monsterSlug,
                effect = effect
            )
        )
    }
    val addMonsterUnit = { unitNumbers: List<Int>, monsterSlug: String, isElite: Boolean ->
        viewModel.onAction(
            ScenarioActions.AddUnits(
                numbers = unitNumbers,
                monsterSlug = monsterSlug,
                isElite = isElite
            )
        )
    }
    val nextRound = { viewModel.onAction(ScenarioActions.NextRound) }
    val clickMagic = { magic: MagicUi ->
        viewModel.onAction(ScenarioActions.UpdateMagic(magic))
    }
    val changeUnitLevel = { monsterSlug: String, unit: MonsterUnit, level: Int ->
        viewModel.onAction(
            ScenarioActions.UpdateUnitLevel(
                monsterSlug = monsterSlug,
                unitNumber = unit.number,
                level = level,
                isElite = unit.isSpecial
            )
        )
    }

    when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            ScenarioScreen(
                state = uiState,
                addMonster = addMonster,
                back = back,
                complete = complete,
                deleteMonster = deleteMonster,
                deleteUnit = deleteUnit,
                updateUnitLife = updateUnitLife,
                switchUnitEffect = switchUnitEffect,
                addMonsterUnit = addMonsterUnit,
                nextRound = nextRound,
                clickMagic = clickMagic,
                changeUnitLevel = changeUnitLevel
            )
        }
        else -> {
            ScenarioScreen(
                state = uiState,
                addMonster = addMonster,
                back = back,
                complete = complete,
                deleteMonster = deleteMonster,
                deleteUnit = deleteUnit,
                updateUnitLife = updateUnitLife,
                switchUnitEffect = switchUnitEffect,
                addMonsterUnit = addMonsterUnit,
                nextRound = nextRound,
                clickMagic = clickMagic,
                changeUnitLevel = changeUnitLevel
            )
        }
    }
    if (uiState.showMonsterDialog) {
        MonsterListDialog(
            monsters = uiState.monstersForAdd,
            selectMonster = { viewModel.onAction(ScenarioActions.AddMonster(it)) },
            onDismiss = { viewModel.onAction(ScenarioActions.CloseMonstersDialog) },
            addNewMonsters = { viewModel.onAction(ScenarioActions.AddNewMonsters) }
        )
    }
}