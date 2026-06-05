package com.rumpilstilstkin.gloomhavenhelper.navigation

import kotlinx.serialization.Serializable

sealed interface GlHelperScreen {
    @Serializable
    data object Start : GlHelperScreen

    @Serializable
    data object EditCurrentTeam : GlHelperScreen

    @Serializable
    data object Scenario : GlHelperScreen

    @Serializable
    data class CharacterDetails(
        val characterId: Int,
    ) : GlHelperScreen

    @Serializable
    data class AddGoodsForCharacter(
        val characterId: Int,
    ) : GlHelperScreen

    @Serializable
    data class SearchPersonalQuest(
        val characterId: Int,
    ) : GlHelperScreen

    @Serializable
    data object AddGoodsForTeam : GlHelperScreen

    @Serializable
    data object AddScenarioForTeam : GlHelperScreen

    @Serializable
    data object TeamAchievements : GlHelperScreen

    @Serializable
    data object GlobalAchievements : GlHelperScreen

    @Serializable
    data object ScenarioConstructor : GlHelperScreen

    @Serializable
    data object Settings : GlHelperScreen
}
