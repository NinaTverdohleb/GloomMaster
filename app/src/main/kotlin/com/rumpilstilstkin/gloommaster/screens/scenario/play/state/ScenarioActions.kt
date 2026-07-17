package com.rumpilstilstkin.gloommaster.screens.scenario.play.state

import com.rumpilstilstkin.gloommaster.domain.entity.Magic
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterStatType

sealed interface ScenarioActions {
    data class AddUnits(
        val monsterSlug: String,
    ) : ScenarioActions

    data class RemoveUnit(
        val number: Int,
        val monsterSlug: String,
    ) : ScenarioActions

    data class AddMonster(
        val monsterSlugs: List<String>,
    ) : ScenarioActions

    data class RemoveMonster(
        val monsterSlug: String,
    ) : ScenarioActions

    data class UpdateUnitLife(
        val newValue: Int,
        val monsterSlug: String,
        val unitNumber: Int,
    ) : ScenarioActions

    data object NextRound : ScenarioActions

    data class SwitchUnitEffect(
        val effect: MonsterStatType,
        val monsterSlug: String,
        val unitNumber: Int,
    ) : ScenarioActions

    data class UpdateMagic(
        val magic: Magic,
    ) : ScenarioActions

    data class UpdateUnitLevel(
        val monsterSlug: String,
        val unitNumber: Int,
    ) : ScenarioActions

    data object AddNewMonsters : ScenarioActions

    data object OpenAddMonster : ScenarioActions

    data object OpenComplete : ScenarioActions
}
