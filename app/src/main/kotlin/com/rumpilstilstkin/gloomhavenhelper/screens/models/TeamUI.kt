package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class TeamUI(
    val teamId: Int,
    val teamLevel: Int,
    val teamName: String,
    val teamReputation: Int,
    val prosperity: Prosperity,
    val shopDiscount: Int,
    val teamAchievements: String,
    val globalAchievements: String,
    val teamScenario: ImmutableList<ShortScenarioUI>,
    val characters: ImmutableList<CharacterUI>,
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
            teamScenario = persistentListOf(
                ShortScenarioUI.fixture(1),
                ShortScenarioUI.fixture(2),
            ),
            characters = persistentListOf(
                CharacterUI.fixture()
            ),
        )
    }
}

data class ShortTeamInfoUi(
    val teamId: Int,
    val teamName: String,
)