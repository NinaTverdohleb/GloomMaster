package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
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
    val teamAchievements: ImmutableList<AchievementWithName>,
    val globalAchievements: ImmutableList<AchievementWithName>,
    val teamScenario: ImmutableList<ShortScenarioUI>,
    val characters: ImmutableList<CharacterUI>,
    val canAddCharacter: Boolean = false,
    val hasActiveScenario: Boolean,
    val churchValue: Int,
    val churchValueForNextProsperity: Int,
) {
    companion object {
        fun fixture() =
            TeamUI(
                teamId = 1,
                teamLevel = 3,
                teamName = "Team 1",
                teamReputation = 1,
                prosperity = Prosperity.fixture(),
                teamAchievements =
                    persistentListOf(
                        AchievementWithName.fixture("Achievement 1"),
                        AchievementWithName.fixture("Achievement 2"),
                    ),
                globalAchievements =
                    persistentListOf(
                        AchievementWithName.fixture("Achievement 1"),
                        AchievementWithName.fixture("Achievement 2"),
                    ),
                shopDiscount = 0,
                teamScenario =
                    persistentListOf(
                        ShortScenarioUI.fixture(1),
                        ShortScenarioUI.fixture(2),
                    ),
                characters =
                    persistentListOf(
                        CharacterUI.fixture(),
                    ),
                hasActiveScenario = true,
                churchValue = 100,
                churchValueForNextProsperity = 150,
            )
    }
}

data class ShortTeamInfoUi(
    val teamId: Int,
    val teamName: String,
)
