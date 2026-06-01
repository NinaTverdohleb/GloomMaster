package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType

sealed interface ScenarioActions {
    data class AddUnits(val numbers: List<Int>, val monsterSlug: String, val isElite: Boolean) :
        ScenarioActions

    data class RemoveUnit(val number: Int, val monsterSlug: String) : ScenarioActions
    data object CompleteScenario : ScenarioActions
    data class AddMonster(val monsterSlugs: List<String>) : ScenarioActions
    data class RemoveMonster(val monsterSlug: String) : ScenarioActions
    data class UpdateUnitLife(val newValue: Int, val monsterSlug: String, val unitNumber: Int) :
        ScenarioActions

    data object NextRound : ScenarioActions
    data class SwitchUnitEffect(val effect: MonsterStatType, val monsterSlug: String, val unitNumber: Int) :
        ScenarioActions

    data object OpenMonstersDialog : ScenarioActions
    data object CloseMonstersDialog : ScenarioActions
    data class UpdateMagic(val magic: MagicUi) : ScenarioActions
    data class UpdateUnitLevel(
        val monsterSlug: String,
        val unitNumber: Int,
        val level: Int,
        val isElite: Boolean
    ) : ScenarioActions

    data object ShowUnitLevelDialog : ScenarioActions
    data object CloseUnitLevelDialog : ScenarioActions
    data object AddNewMonsters : ScenarioActions
}