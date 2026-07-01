package com.rumpilstilstkin.gloomhavenhelper.navigation.events

import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen

sealed interface GlHelperEvent {
    data class Screen(
        val screen: GlHelperScreen,
    ) : GlHelperEvent

    /**
     * Navigate to [screen] and pop [popUpTo] off the back stack (inclusive), so the
     * current screen is replaced rather than stacked — e.g. finishing onboarding
     * and moving to Start without leaving onboarding in the back stack.
     */
    data class Replace(
        val screen: GlHelperScreen,
        val popUpTo: GlHelperScreen,
    ) : GlHelperEvent

    data object Back : GlHelperEvent
}
