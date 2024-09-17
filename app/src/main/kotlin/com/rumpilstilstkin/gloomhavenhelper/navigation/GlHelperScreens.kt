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
}