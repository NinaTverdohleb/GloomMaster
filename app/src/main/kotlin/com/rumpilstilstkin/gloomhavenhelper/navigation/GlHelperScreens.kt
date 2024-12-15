package com.rumpilstilstkin.gloomhavenhelper.navigation

import kotlinx.serialization.Serializable

sealed interface GlHelperScreens {
    @Serializable
    data object Start : GlHelperScreens
    @Serializable
    data object TeamCreate : GlHelperScreens
    @Serializable
    data object TeamDetails : GlHelperScreens
    @Serializable
    data object Scenario : GlHelperScreens
    @Serializable
    data class CharacterDetails(val characterId: Int): GlHelperScreens
    @Serializable
    data class AddGoodsForCharacter(val characterId: Int): GlHelperScreens
    @Serializable
    data class SearchPersonalQuest(val characterId: Int): GlHelperScreens
}