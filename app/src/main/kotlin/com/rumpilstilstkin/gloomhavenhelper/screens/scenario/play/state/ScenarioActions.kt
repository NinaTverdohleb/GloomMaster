package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import com.rumpilstilstkin.gloomhavenhelper.screens.models.ActionUi

sealed interface ScenarioActions {
    data class AddUnits(val numbers: List<Int>, val monsterId: Int, val isElite: Boolean) :
        ScenarioActions

    data class RemoveUnit(val number: Int, val monsterId: Int) : ScenarioActions
    data object CompleteScenario : ScenarioActions
    data class AddMonster(val monsterIds: List<Int>) : ScenarioActions
    data class RemoveMonster(val monsterId: Int) : ScenarioActions
    data class UpdateUnitLife(val newValue: Int, val monsterId: Int, val unitNumber: Int) :
        ScenarioActions

    data object NextRound : ScenarioActions
    data class SwitchUnitEffect(val effect: ActionUi, val monsterId: Int, val unitNumber: Int) :
        ScenarioActions

    data object OpenMonstersDialog : ScenarioActions
    data object CloseMonstersDialog : ScenarioActions
    data class UpdateMagic(val magic: Magic) : ScenarioActions
    data class UpdateUnitLevel(
        val monsterId: Int,
        val unitNumber: Int,
        val level: Int,
        val isElite: Boolean
    ) : ScenarioActions

    data object ShowUnitLevelDialog : ScenarioActions
    data object CloseUnitLevelDialog : ScenarioActions
}