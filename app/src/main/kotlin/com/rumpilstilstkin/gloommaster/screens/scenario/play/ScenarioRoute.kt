package com.rumpilstilstkin.gloommaster.screens.scenario.play

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloommaster.domain.entity.Magic
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloommaster.screens.core.LaunchedScreenEffect
import com.rumpilstilstkin.gloommaster.screens.scenario.play.state.ScenarioActions

@Composable
fun ScenarioRoute(
    navController: NavHostController,
    widthSizeClass: WindowWidthSizeClass,
    viewModel: ScenarioViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(viewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(viewModel)
        }
    }
    val addScenarioMonsters = { viewModel.onAction(ScenarioActions.OpenAddMonster) }
    val addNewMonsters = { viewModel.onAction(ScenarioActions.AddNewMonsters) }
    val back: () -> Unit = { navController.popBackStack() }
    val complete = { viewModel.onAction(ScenarioActions.OpenComplete) }
    val deleteMonster =
        { monsterSlug: String -> viewModel.onAction(ScenarioActions.RemoveMonster(monsterSlug)) }
    val deleteUnit = { unitNumber: Int, monsterSlug: String ->
        viewModel.onAction(
            ScenarioActions.RemoveUnit(
                number = unitNumber,
                monsterSlug = monsterSlug,
            ),
        )
    }
    val updateUnitLife = { unitNumber: Int, monsterSlug: String, life: Int ->
        viewModel.onAction(
            ScenarioActions.UpdateUnitLife(
                unitNumber = unitNumber,
                monsterSlug = monsterSlug,
                newValue = life,
            ),
        )
    }
    val switchUnitEffect = { unitNumber: Int, monsterSlug: String, effect: MonsterStatType ->
        viewModel.onAction(
            ScenarioActions.SwitchUnitEffect(
                unitNumber = unitNumber,
                monsterSlug = monsterSlug,
                effect = effect,
            ),
        )
    }
    val addMonsterUnits = { monsterSlug: String ->
        viewModel.onAction(
            ScenarioActions.AddUnits(
                monsterSlug = monsterSlug,
            ),
        )
    }
    val nextRound = { viewModel.onAction(ScenarioActions.NextRound) }
    val clickMagic = { magic: Magic ->
        viewModel.onAction(ScenarioActions.UpdateMagic(magic))
    }
    val changeUnitLevel = { unitNumber: Int, monsterSlug: String ->
        viewModel.onAction(
            ScenarioActions.UpdateUnitLevel(
                monsterSlug = monsterSlug,
                unitNumber = unitNumber,
            ),
        )
    }
    val openRandom = { viewModel.onAction(ScenarioActions.OpenRandom) }

    ScenarioScreen(
        state = uiState,
        addScenarioMonsters = addScenarioMonsters,
        addNewMonsters = addNewMonsters,
        back = back,
        complete = complete,
        deleteMonster = deleteMonster,
        deleteUnit = deleteUnit,
        updateUnitLife = updateUnitLife,
        switchUnitEffect = switchUnitEffect,
        addMonsterUnits = addMonsterUnits,
        nextRound = nextRound,
        clickMagic = clickMagic,
        onLevel = changeUnitLevel,
        openRandom = openRandom,
    )

    LaunchedScreenEffect(
        effects = viewModel.screenEvents,
        navController = navController,
    )
}
