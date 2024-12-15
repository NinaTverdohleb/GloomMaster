package com.rumpilstilstkin.gloomhavenhelper.navigation.events

import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens

sealed interface GlHelperEvent {
    data class Screen(val screen: GlHelperScreens): GlHelperEvent
    data object Back: GlHelperEvent
}