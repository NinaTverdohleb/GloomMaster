package com.rumpilstilstkin.gloomhavenhelper.navigation.events

import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen

sealed interface GlHelperEvent {
    data class Screen(
        val screen: GlHelperScreen,
    ) : GlHelperEvent

    data object Back : GlHelperEvent
}

