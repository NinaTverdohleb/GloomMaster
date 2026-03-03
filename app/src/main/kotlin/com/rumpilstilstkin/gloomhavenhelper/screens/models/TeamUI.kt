package com.rumpilstilstkin.gloomhavenhelper.screens.models

import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import com.rumpilstilstkin.gloomhavenhelper.screens.draft.MainScreenAction

data class TeamUI(
    val teamId: Int,
    val teamLevel: Int,
    val teamName: String,
    val teamReputation: Int,
    val prosperity: Prosperity,
    val shopDiscount: Int,
    val teamAchievements: String,
    val globalAchievements: String,
    val teamScenario: List<ShortScenarioUI>,
    val characters: List<CharacterUI>,
    val canAddCharacter: Boolean = false,
) {
    companion object {
        fun fixture() = TeamUI(
            teamId = 1,
            teamLevel = 3,
            teamName = "Team 1",
            teamReputation = 1,
            prosperity = Prosperity.fixture(),
            teamAchievements = "Первые шаги",
            globalAchievements = "Сбежавшая торговка",
            shopDiscount = 0,
            teamScenario = listOf(
                ShortScenarioUI.fixture(1),
                ShortScenarioUI.fixture(2),
            ),
            characters = listOf(
                CharacterUI(
                    name = "Character 1",
                    level = 1,
                    characterClass = CharacterClassUI(
                        classType = CharacterClassType.Brute,
                        name = "Class 1",
                        imageRes = R.drawable.br,
                    ),
                    teamName = null
                )
            ),
        )
    }
}